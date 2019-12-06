package skhu.ht.hotthink.api.idea.controller;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skhu.ht.hotthink.api.domain.enums.BoardType;
import skhu.ht.hotthink.api.domain.enums.IdeaState;
import skhu.ht.hotthink.api.idea.exception.IdeaInvalidException;
import skhu.ht.hotthink.api.idea.model.CategoryDTO;
import skhu.ht.hotthink.api.idea.model.PutDTO;
import skhu.ht.hotthink.api.idea.model.boardin.RealInDTO;
import skhu.ht.hotthink.api.idea.model.boardlist.RealListDTO;
import skhu.ht.hotthink.api.idea.model.boardout.RealOutDTO;
import skhu.ht.hotthink.api.idea.model.page.IdeaPagination;
import skhu.ht.hotthink.api.idea.model.page.Pagination;
import skhu.ht.hotthink.api.idea.service.BoardServiceImpl;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@RestController
@RequestMapping("api/realthink")
public class RealThinkController {
    @Autowired
    BoardServiceImpl boardService;
    /*
            작성자: 홍민석
            작성일: 2019-10-07
            내용: realthink 게시물 리스트 READ.
            Pagination 정보를 JSON으로 입력받아
            해당하는 realthink 게시물 리스트 반환
    */
    @GetMapping
    public ResponseEntity<?> realList(@RequestParam(value = "sb",defaultValue = "0") Integer searchBy,
                                      @RequestParam("sz") @NonNull Integer size,
                                      @RequestParam("pg") @NonNull Integer page,
                                      @RequestParam(name="ob", defaultValue = "0") Integer orderBy,
                                      @RequestParam(name="category") CategoryDTO category,
                                      @RequestParam(name="st", required = false) String searchText,
                                      @RequestParam(name="state",required = false) IdeaState ideaState) {
        IdeaPagination pagination = IdeaPagination.ideaBuilder()
                .category(category.name())
                .page(page)
                .boardType(BoardType.REAL)
                .size(size)
                .orderBy(orderBy)
                .searchBy(searchBy)
                .searchText(searchText)
                .type(ideaState)
                .build();
        List<RealListDTO> real = boardService.getBoardList(pagination,RealListDTO.class);
        return new ResponseEntity(real, HttpStatus.OK);
    }

    /*
        작성자: 홍민석
        작성일: 2019-10-01
        내용: realthink 게시물 READ.
        realthink 게시물 id_seq를 입력하면
        해당하는 realthink 게시물 반환
    */
    @GetMapping(value = "/{realId}")
    public ResponseEntity<?> realRead(@PathVariable("realId") Long realId) {
        RealOutDTO realOutDto = boardService.getOne(realId,BoardType.REAL, RealOutDTO.class);
        return new ResponseEntity(realOutDto,HttpStatus.OK);
    }

    /*
        작성자: 홍민석
        작성일: 2019-12-02
        내용: FreePass권 1회 소모 후
        real Think 작성
    */
    @PostMapping(value = "/{category}")
    public ResponseEntity<?> realCreate(@RequestBody RealInDTO realInDto,
                                        @PathVariable("category") String category) {
        if(boardService.setOne(realInDto, category, BoardType.REAL)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    /*
        작성자: 홍민석
        작성일: 2019-10-07
        내용: realthink 게시물 CREATE.
        쓰고자 하는 게시물 정보(RealInDTO)를 JSON으로 입력받아
        새로운 게시물 생성
        작성일: 2019-11-26
        내용: Hot think를 real Think로 전환.
    */
    @PostMapping(value = "{boardId}/category/{category}")
    public ResponseEntity<?> realCreate(@RequestBody RealInDTO realInDto,
                                        @PathVariable Long boardId,
                                        @PathVariable("category") String category){
        if(realInDto.getTitle().isEmpty()||realInDto.getContents().isEmpty())
            throw new IdeaInvalidException("제목과 내용이 없습니다.");
        if(boardService.isHotThink(boardId)) {
            PutDTO putDTO = PutDTO.builder()
                    .boardType(BoardType.REAL)
                    .contents(realInDto.getContents())
                    .title(realInDto.getTitle())
                    .bdSeq(boardId)
                    .real(realInDto.getReal())
                    .build();
            if (boardService.putOne(putDTO)) {
                return new ResponseEntity(HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /*
        작성자: 홍민석
        작성일: 2019-10-07
        내용: realthink 게시물 UPDATE.
        수정하고자 하는 게시물 정보(RealInDTO)를 JSON으로 입력받아
        원본 게시물 수정.
    */
    @PutMapping(value = "/{realId}/{category}")
    public ResponseEntity<?> realUpdate(@PathVariable("realId") Long realId, @PathVariable("category") String category,
                                             @RequestBody RealInDTO realInDto){
        PutDTO putDto = PutDTO.builder()
                .bdSeq(realId)
                .title(realInDto.getTitle())
                .contents(realInDto.getContents())
                .real(realInDto.getReal())
                .boardType(BoardType.REAL)
                .build();

        if(boardService.putOne(putDto)) return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);

    }

    /*
        작성자: 홍민석
        작성일: 2019-10-07
        내용: realthink 게시물 DELETE.
        수정하고자 하는 게시물 번호를 입력받아 해당 게시물 삭제.
        삭제 실패시 BAD_REQUEST 반환.
    */
    @DeleteMapping(value = "/{realId}")
    public ResponseEntity<?> realDelete(@PathVariable("realId") Long realId){
        if(boardService.deleteOne(realId, null)) return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
