package br.jus.tjro.to_do_list.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.tjro.to_do_list.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Busca um usuário pelo email
    Optional<User> findByEmail(String email);
}
