package edu.du.sb1022secu4.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data   //Getter Setter 
@Builder //DTO -> Entity화
@AllArgsConstructor //모든 컬럼 생성자
@NoArgsConstructor//기본 생성자
@Table(name = "member")
public class Member {
    @Id // pk 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increament
    private Long id;

    @Column(nullable = false,unique = true)
    private String email;

    @Column
    private String username;

    @Column(nullable = false)
    private String password;

    public Member(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
