package skhu.ht.hotthink.api.idea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skhu.ht.hotthink.api.domain.Reply;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    @Query(value = "SELECT R FROM Reply R INNER JOIN R.board B WHERE B.bdSeq = ?1")
    public List<Reply> findReplyByBdSeq(Long bdSeq);
    Reply findReplyByRpSeq(Long rpSeq);
    @Query(value = "SELECT R FROM Reply R INNER JOIN R.board B WHERE R.rpSeq =?1 AND B.bdSeq = ?2")
    public Reply findReplyByRpSeqAndBdSeq(Long rpSeq, Long bdSeq);

    @Query(value = "UPDATE Reply R SET R.good = R.good + 1 WHERE R.rpSeq =?1 AND R.board IN (SELECT B FROM Reply R1 JOIN R1.board B WHERE B.bdSeq = ?2)")
    Integer likeReplyByRpSeqAndBdSeq(Long replyId, Long boardId);
}
