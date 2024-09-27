package br.jus.tjro.to_do_list.service;

import org.springframework.stereotype.Service;

import br.jus.tjro.to_do_list.model.Task;
import br.jus.tjro.to_do_list.model.User;
import br.jus.tjro.to_do_list.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    // Injeção via construtor
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Recupera as tarefas de um usuário
    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByUser(user);
    }

    // Recupera uma tarefa por ID e usuário
    public Optional<Task> getTaskByIdAndUser(Long id, User user) {
        return taskRepository.findById(id)
                .filter(task -> task.getUser().equals(user));
    }

    // Salva uma tarefa
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    // Deleta uma tarefa
    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }
}
