package skhu.ht.hotthink.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skhu.ht.hotthink.model.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmail(String email);
}
