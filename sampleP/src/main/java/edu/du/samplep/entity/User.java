package edu.du.samplep.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "users")
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String username;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}