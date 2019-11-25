package skhu.ht.hotthink.api.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skhu.ht.hotthink.api.domain.Subscribe;

import java.util.Date;
@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {
    @Query(value = "getEND_DAY(?1)",nativeQuery=true)
    Date getEndDay(Integer period);
    @Query(value = "SELECT S FROM Subscribe S INNER JOIN S.user U WHERE U.email = ?1")
    Subscribe findSubscribeByUserEmail(String email);
}
