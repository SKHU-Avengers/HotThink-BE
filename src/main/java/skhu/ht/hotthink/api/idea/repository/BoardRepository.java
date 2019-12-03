package skhu.ht.hotthink.api.idea.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skhu.ht.hotthink.api.domain.Board;
import skhu.ht.hotthink.api.domain.Category;
import skhu.ht.hotthink.api.domain.Like;
import skhu.ht.hotthink.api.domain.User;
import skhu.ht.hotthink.api.domain.enums.BoardType;
import skhu.ht.hotthink.api.idea.model.page.IdeaPagination;
import skhu.ht.hotthink.api.idea.model.page.Option;
import skhu.ht.hotthink.api.idea.model.page.Pagination;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    default List<Board> findAll(Pagination pagination,Category category){
        Pageable pageable = PageRequest.of(pagination.getPage()-1, pagination.getSize(), pagination.getOrderBy());
        Page<Board> page;
        String searchText = pagination.getSearchText();
        BoardType boardType = pagination.getBoardType();

        switch(pagination.getSearchBy()){
            case 작성자_닉네임:
                page = this.findByCategoryAndUserAndBoardType(category,searchText, boardType, pageable);
                break;
            case 제목:
                page = this.findByCategoryAndTitleContainsAndBoardType(category, searchText, boardType, pageable);
                break;
            case 내용:
                page = this.findByCategoryAndContentsContainsAndBoardType(category, searchText, boardType, pageable);
                break;
            default:
                page = this.findByCategoryAndBoardType(category, boardType, pageable);
        }
        return page.getContent();
    }

    @Query(value = "SELECT getBD_Seq(?1, ?2)", nativeQuery = true)
    Long findBoardSeq(String category, String boardType);

    //@Query(value = "SELECT BD.BD_SEQ FROM TB_BOARD BD INNER JOIN CATEGORY CT ON BD.CT_CODE = CT.CODE WHERE FR.SEQ = ?1 AND CT.CATEGORY = ?2", nativeQuery = true)
    //Long findBdSeq(Long seq, String category);

    //@Query("SELECT L FROM ELike L JOIN Board B WHERE B.boardType=?1")
    //List<Like> findLikeByBdSeqAndBoardType(Long bdSeq, BoardType boardType);

    //내용으로 검색
    Page<Board> findByCategoryAndContentsContainsAndBoardType(Category category, String Contents, BoardType boardType, Pageable pageable);
    //글 제목으로 검색
    Page<Board> findByCategoryAndTitleContainsAndBoardType(Category category, String Title, BoardType boardType, Pageable pageable);
    Page<Board> findByCategoryAndUserAndBoardType(Category category, String User, BoardType boardType, Pageable pageable);
    //기본 검색
    Page<Board> findByCategoryAndBoardType(Category category, BoardType boardType, Pageable pageable);

    Board findBoardByBdSeq(Long bdSeq);

    List<Board> findAllByUser(User entity);
}