package br.jus.tjro.to_do_list.dto;

public class AuthenticationResponse {

    private String jwt;

    // Construtores
    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    // Getters e Setters
    public String getJwt() {
        return jwt;
    }


    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

}
