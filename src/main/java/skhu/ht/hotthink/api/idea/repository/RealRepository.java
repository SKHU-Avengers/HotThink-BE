package skhu.ht.hotthink.api.idea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import skhu.ht.hotthink.api.domain.Real;

public interface RealRepository extends JpaRepository<Real, Integer> {
    @Modifying
    @Query("UPDATE Real R SET R.state = ?2 WHERE R.seq = ?1")
    void putState(Long rlSeq, String IdeaState);
}
