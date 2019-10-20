package skhu.ht.hotthink.api.idea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skhu.ht.hotthink.api.domain.ReplyLike;

@Repository
public interface ReplyLikeRepository extends JpaRepository<ReplyLike,Integer> {
}
