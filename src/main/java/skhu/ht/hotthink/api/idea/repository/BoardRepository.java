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
import skhu.ht.hotthink.api.domain.enums.UseAt;
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
                page = this.findByCategoryAndUserAndBoardTypeAndUseAt(category,searchText, boardType, UseAt.Y, pageable);
                break;
            case 제목:
                page = this.findByCategoryAndTitleContainsAndBoardTypeAndUseAt(category, searchText, boardType, UseAt.Y, pageable);
                break;
            case 내용:
                page = this.findByCategoryAndContentsContainsAndBoardTypeAndUseAt(category, searchText, boardType, UseAt.Y, pageable);
                break;
            default:
                page = this.findByCategoryAndBoardTypeAndUseAt(category, boardType, UseAt.Y, pageable);
        }
        return page.getContent();
    }

    @Query(value = "SELECT getBD_Seq(?1, ?2)", nativeQuery = true)
    Long findBoardSeq(String category, String boardType);

    //내용으로 검색
    Page<Board> findByCategoryAndContentsContainsAndBoardTypeAndUseAt(Category category, String Contents, BoardType boardType, UseAt useAt, Pageable pageable);
    //글 제목으로 검색
    Page<Board> findByCategoryAndTitleContainsAndBoardTypeAndUseAt(Category category, String Title, BoardType boardType, UseAt useAt, Pageable pageable);
    Page<Board> findByCategoryAndUserAndBoardTypeAndUseAt(Category category, String User, BoardType boardType, UseAt useAt, Pageable pageable);
    //기본 검색
    Page<Board> findByCategoryAndBoardTypeAndUseAt(Category category, BoardType boardType, UseAt useAt, Pageable pageable);
    @Query("SELECT B FROM Board B WHERE B.useAt='Y' AND B.bdSeq=?1")
    Board findBoardByBdSeq(Long bdSeq);

    @Query("SELECT B FROM Board B WHERE B.useAt='Y' AND B.user=?1")
    List<Board> findAllByUser(User entity);
}