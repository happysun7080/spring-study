package gwshin.jwt.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity  // 데이터베이스의 테이블과 1대1 매핑되는 객체
@Table(name = "`user`")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id  // Primary Key
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 자동 증가
    private Long userId;

    @Column(name = "username", length = 50, unique = true)
    private String username;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "activated")
    private boolean activated;

    // 다대일, 일대다 관계의 조인 테이블로 정의
    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
}
