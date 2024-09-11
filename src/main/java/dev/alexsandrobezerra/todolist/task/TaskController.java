package dev.alexsandrobezerra.todolist.task;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public ResponseEntity list(HttpServletRequest request) {
        final var userId = UUID.fromString(request.getAttribute("userId").toString());
        var tasks = this.taskRepository.findByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        final var userId = UUID.fromString(request.getAttribute("userId").toString());
        taskModel.setUserId(userId);

        final var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStartedAt()) || currentDate.isAfter(taskModel.getFinishedAt())) {
            return ResponseEntity.badRequest().body("Data de início/final anterior a data atual");
        }
        if (taskModel.getStartedAt().isAfter(taskModel.getFinishedAt())) {
            return ResponseEntity.badRequest().body("Data final não pode ser anterior a data inicial");
        }


        var createdTask = this.taskRepository.save(taskModel);
        var location = String.format("/tasks/%s", createdTask.getId());
        return ResponseEntity.created(URI.create(location)).body(createdTask);
    }

}
