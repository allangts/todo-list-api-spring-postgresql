package br.jus.tjro.to_do_list.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import br.jus.tjro.to_do_list.model.User;
import br.jus.tjro.to_do_list.service.UserService;
import br.jus.tjro.to_do_list.security.JwtUtil;
import br.jus.tjro.to_do_list.dto.AuthenticationRequest;
import br.jus.tjro.to_do_list.dto.AuthenticationResponse;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    // Injeção via construtor
    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // Endpoint para autenticação
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authRequest) {

        try {
            // Autentica o usuário usando o AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            // Retorna erro 401 se as credenciais forem inválidas
            return ResponseEntity.status(401).body("Credenciais inválidas.");
        }

        // Carrega os detalhes do usuário
        final UserDetails userDetails = userService.loadUserByUsername(authRequest.getEmail());
        // Gera o token JWT
        final String jwt = jwtUtil.generateToken(userDetails);

        // Retorna o token na resposta
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    // Endpoint para registro de novo usuário
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            // Salva o usuário no banco, impedindo duplicidade de email
            userService.save(user);
            return ResponseEntity.ok("Usuário registrado com sucesso");
        } catch (IllegalStateException e) {
            // Retorna erro 409 (Conflict) se o email já estiver registrado
            return ResponseEntity.status(409).body("O email já está registrado.");
        }
    }
}
