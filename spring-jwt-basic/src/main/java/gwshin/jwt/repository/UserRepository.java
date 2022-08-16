package gwshin.jwt.repository;

import gwshin.jwt.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * User 엔티티에 매핑됨. findAll, save와 같은 기본적인 CRUD 메소드 사용 가능
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * username을 기준으로 User 정보를 가져올 때 권한 정보도 같이 가져온다.
     */
    @EntityGraph(attributePaths = "authorities")  // 쿼리가 수행될 때 Lazy 조회가 아닌 Eager 조회로 authorities 정보를 같이 가져옴
    Optional<User> findOneWithAuthoritiesByUsername(String username);
}
