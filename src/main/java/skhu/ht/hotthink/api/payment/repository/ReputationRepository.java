package skhu.ht.hotthink.api.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skhu.ht.hotthink.api.domain.Board;
import skhu.ht.hotthink.api.domain.Reputation;
@Repository
public interface ReputationRepository extends JpaRepository<Reputation,Integer> {

    Reputation findReputationByBoard(Board board);
}