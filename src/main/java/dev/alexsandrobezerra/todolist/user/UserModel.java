package dev.alexsandrobezerra.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity(name = "tb_users")
public class UserModel {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    private String name;

    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
