package skhu.ht.hotthink.api.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skhu.ht.hotthink.api.domain.Pay;

public interface PayRepository extends JpaRepository<Pay, Integer> {
}
