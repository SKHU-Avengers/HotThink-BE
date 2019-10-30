package skhu.ht.hotthink.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skhu.ht.hotthink.api.domain.EmailConfirm;
import skhu.ht.hotthink.api.domain.User;

public interface EmailConfirmRepository extends JpaRepository<EmailConfirm,Integer> {
    EmailConfirm findEmailConfirmByUser(User user);
}
