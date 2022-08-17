package gwshin.security1.repository;

import gwshin.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// CRUD API를 JpaRepository가 들고 있음
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * select * from user where username=?
     */
    User findByUsername(String username);  // JPA Query Method
}
