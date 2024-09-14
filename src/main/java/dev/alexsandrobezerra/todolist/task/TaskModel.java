package dev.alexsandrobezerra.todolist.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class TaskModel {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    private UUID userId;

    @Column(length = 50)
    private String title;

    private String description;

    private String priority;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setTitle(String title) throws Exception {
        if (title.length() > 50) {
            throw new Exception("The title field must contain a maximum of 50 characters.");
        }
        this.title = title.trim();
    }
}
