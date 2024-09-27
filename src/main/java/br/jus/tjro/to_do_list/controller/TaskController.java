package br.jus.tjro.to_do_list.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.jus.tjro.to_do_list.model.Task;
import br.jus.tjro.to_do_list.model.User;
import br.jus.tjro.to_do_list.service.TaskService;
import br.jus.tjro.to_do_list.service.UserService;

import java.security.Principal;
import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    // Injeção via construtor
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    // Lista todas as tarefas do usuário autenticado
    @GetMapping
    public ResponseEntity<List<Task>> getTasks(Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        List<Task> tasks = user.getTasks(); // Navegação direta de User para Task
        return ResponseEntity.ok(tasks);
    }

    // Cria uma nova tarefa para o usuário autenticado
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        user.addTask(task); // Adiciona a tarefa ao usuário
        userService.save(user); // Salva o usuário (a tarefa será salva em cascata)
        return ResponseEntity.ok(task);
    }

    // Atualiza uma tarefa existente
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task taskDetails, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        Task task = taskService.getTaskByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setDueDate(taskDetails.getDueDate());
        task.setCompleted(taskDetails.isCompleted());

        taskService.saveTask(task);
        return ResponseEntity.ok(task);
    }

    // Deleta uma tarefa existente
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        Task task = taskService.getTaskByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        user.removeTask(task); // Remove a tarefa do usuário
        userService.save(user); // Salva o usuário (a tarefa será removida em cascata)
        return ResponseEntity.ok("Tarefa deletada com sucesso");
    }
}
