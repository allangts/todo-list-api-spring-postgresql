package br.jus.tjro.to_do_list.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.tjro.to_do_list.model.Task;
import br.jus.tjro.to_do_list.model.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Recupera as tarefas de um usuário específico
    List<Task> findByUser(User user);
}
