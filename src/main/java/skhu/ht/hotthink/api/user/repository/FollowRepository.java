package skhu.ht.hotthink.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skhu.ht.hotthink.api.domain.Follow;
import skhu.ht.hotthink.api.domain.User;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Integer> {
    List<Follow> findAllByCelebrity(User celebrity);
    List<Follow> findAllByFollower(User follower);

    Follow findFollowByFollowerAndCelebrity(User from, User to);
}
