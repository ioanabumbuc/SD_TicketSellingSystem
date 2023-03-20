package sd.ticketsellingsystem.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Base64;

@Entity
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;

    @PrePersist
    public void prePersist() {
        this.password = Base64.getEncoder().encodeToString(password.getBytes());
    }

    public boolean checkPassword(String password) {
        return Arrays.equals(Base64.getDecoder().decode(this.password), password.getBytes());
    }


    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }


    public Long getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;//Base64.getEncoder().encodeToString(password.getBytes());
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
