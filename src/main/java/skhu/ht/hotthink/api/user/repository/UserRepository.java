package skhu.ht.hotthink.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skhu.ht.hotthink.api.domain.User;
import skhu.ht.hotthink.api.user.model.UserModificationDTO;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmail(String email);
    User findUserByNickName(String nickName);
    @Modifying
    @Query("UPDATE User U SET U.realTicket = U.realTicket + 1 WHERE U.email =?1")
    void increaseRealTicket(String email);
    @Modifying
    @Query("UPDATE User U SET U.point = U.point - ?2 WHERE U.email = ?1")
    void purchase(String email, int price);

    @Modifying
    @Query("UPDATE User U SET U.point = U.point + ?2 WHERE U.email = ?1")
    void pointCharge(String email, int point);
}
