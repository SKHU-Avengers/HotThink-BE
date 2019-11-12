package skhu.ht.hotthink.api.idea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skhu.ht.hotthink.api.domain.Like;
import skhu.ht.hotthink.api.domain.User;
import skhu.ht.hotthink.api.domain.enums.BoardType;

@Repository
public interface LikeRepository extends JpaRepository<Like,Integer> {
    Like findByBdSeqAndBoardTypeAndUser(Long seq, BoardType boardType, User user);
}
