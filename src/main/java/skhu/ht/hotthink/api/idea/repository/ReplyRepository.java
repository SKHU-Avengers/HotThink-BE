package skhu.ht.hotthink.api.idea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skhu.ht.hotthink.api.domain.Board;
import skhu.ht.hotthink.api.domain.Reply;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    @Query(value = "SELECT R FROM Reply R INNER JOIN R.board B WHERE R.superSeq IS NULL AND B.bdSeq = ?1")
    public List<Reply> findReplyByBdSeq(Long bdSeq);
    Reply findReplyByRpSeq(Long rpSeq);
    public Reply findReplyByRpSeqAndBoard(Long rpSeq, Board board);
    @Query(value = "SELECT COUNT(R) FROM Reply R WHERE R.superSeq = ?1")
    public Long countRepliesBySuperSeq(Long SuperSeq);
}
