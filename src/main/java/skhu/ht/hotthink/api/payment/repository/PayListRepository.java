package skhu.ht.hotthink.api.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skhu.ht.hotthink.api.domain.PayList;
@Repository
public interface PayListRepository extends JpaRepository<PayList, Integer> {

}