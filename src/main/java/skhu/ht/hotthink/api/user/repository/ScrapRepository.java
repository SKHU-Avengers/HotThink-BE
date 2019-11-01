package skhu.ht.hotthink.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skhu.ht.hotthink.api.domain.Board;
import skhu.ht.hotthink.api.domain.Scrap;
import skhu.ht.hotthink.api.domain.User;

import java.util.Collection;
import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap,Integer> {
    List<Scrap> findAllByUser(User user);
    @Query("SELECT S FROM Scrap S INNER JOIN S.board B WHERE S.user=?1 AND B.boardType = ?2")
    List<Scrap> findAllByUserAndBoardType(User user, String boardType);

    Scrap findScrapByUserAndBoard(User user, Board board);
}
