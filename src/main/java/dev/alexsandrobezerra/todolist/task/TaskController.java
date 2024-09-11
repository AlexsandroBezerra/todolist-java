package dev.alexsandrobezerra.todolist.task;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        taskModel.setUserId(UUID.fromString(request.getAttribute("userId").toString()));
        var createdTask = this.taskRepository.save(taskModel);
        var location = String.format("/tasks/%s", createdTask.getId());
        return ResponseEntity.created(URI.create(location)).body(createdTask);
    }

}
