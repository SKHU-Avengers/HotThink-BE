package skhu.ht.hotthink.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skhu.ht.hotthink.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
