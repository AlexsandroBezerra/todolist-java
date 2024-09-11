package dev.alexsandrobezerra.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

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

    private String username;

    private String name;

    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
