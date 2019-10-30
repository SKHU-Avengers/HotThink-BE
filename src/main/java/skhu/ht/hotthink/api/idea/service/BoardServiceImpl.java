package skhu.ht.hotthink.api.idea.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skhu.ht.hotthink.api.domain.*;
import skhu.ht.hotthink.api.domain.enums.BoardType;
import skhu.ht.hotthink.api.idea.exception.IdeaInvalidException;
import skhu.ht.hotthink.api.idea.exception.IdeaNotFoundException;
import skhu.ht.hotthink.api.idea.exception.ReplyNotFoundException;
import skhu.ht.hotthink.api.idea.model.LikeDTO;
import skhu.ht.hotthink.api.idea.model.PutDTO;
import skhu.ht.hotthink.api.idea.model.boardin.BoardInDTO;
import skhu.ht.hotthink.api.idea.model.boardin.SubRealInDTO;
import skhu.ht.hotthink.api.idea.model.boardlist.BoardListDTO;
import skhu.ht.hotthink.api.idea.model.boardout.BoardOutDTO;
import skhu.ht.hotthink.api.idea.model.page.Pagination;
import skhu.ht.hotthink.api.idea.model.reply.ReplyInDTO;
import skhu.ht.hotthink.api.idea.model.reply.ReplyOutDTO;
import skhu.ht.hotthink.api.idea.repository.*;
import skhu.ht.hotthink.api.user.exception.UserNotFoundException;
import skhu.ht.hotthink.api.user.repository.UserRepository;
import skhu.ht.hotthink.api.idea.repository.RealRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardServiceImpl {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    ReplyRepository replyRepository;
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    RealRepository realRepository;
    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    ModelMapper modelMapper = new ModelMapper();

    /*
        작성자: 홍민석
        작성일: 19-10-04
        내용: Free, Hot, RealThink게시판 리스트를 반환합니다.
    */
    @Transactional
    public <Tlist extends BoardListDTO, Tpage extends Pagination> List<Tlist> getBoardList(Tpage pagination, Class<? extends Tlist> classLiteral) {
        List<Tlist> tlist = boardRepository.findAll(pagination)
                .stream()
                .map(e -> convertTo(e, classLiteral))
                .collect(Collectors.toList());
        if(tlist==null) throw new IdeaNotFoundException();
        return tlist;
    }

    /*
        작성자: 홍민석
        작성일: 19-10-04
        내용: 인자값으로 주어지는 게시물번호와 카테고리를 확인한 후
        해당하는 RealThink게시물을 반환합니다.
    */
    @Transactional
    public <Tout extends BoardOutDTO> Tout getOne(Long seq, Class<? extends Tout> classLiteral){
        Board board = boardRepository.findBoardByBdSeq(seq);
        if(board == null) throw new IdeaNotFoundException();
        board.setHits(board.getHits() + 1);
        boardRepository.save(board);
        return modelMapper.map(board, classLiteral);
    }
    /*
        작성자: 홍민석
        작성일: 19-10-22
        내용: 게시물을 생성합니다.
     */
    @Transactional
    public <Tin extends BoardInDTO> boolean setOne(Tin inDto, String nickname, String category, BoardType boardType) {
        Long seq;
        Board board = modelMapper.map(inDto, Board.class);
        board.setCategory(categoryRepository.findCategoryByCategory(category));
        if ((seq = boardRepository.findBoardSeq(category)) == -1) throw new IdeaInvalidException();
        board.setSeq(seq);
        board.setUser(userRepository.findUserByNickName(nickname));
        if(board.getUser()==null) throw new UserNotFoundException();
        board.setBoardType(boardType);
        board.setHits(0);
        board.setGood(0);
        board.setCreateAt(new Date());
        if (boardRepository.save(board) == null) return false;
        return true;
    }
    /*
            작성자: 홍민석
            작성일: 19-10-26
            내용: FreeThink, RealThink, HotThink 게시물을 수정합니다.
    */
    @Transactional
    public boolean putOne(PutDTO putDto) {
        if(putDto.getBoardType()==null) {
            throw new IdeaInvalidException("Board Type Not Found");
        }
        Board board = boardRepository.findBoardByBdSeq(putDto.getBdSeq());
        BoardInDTO original = modelMapper.map(board,BoardInDTO.class);
        BoardInDTO recent = modelMapper.map(putDto,BoardInDTO.class);

        if(!original.getTitle().equals(recent.getTitle())||
                !original.getContents().equals(recent.getContents())||
                !original.getImage().equals(recent.getImage())) {
            History history = modelMapper.map(original,History.class);
            history.setSeq(putDto.getBdSeq());
            history.setBoard(board);
            history.setUpdateAt(new Date());
            historyRepository.save(history);
            board.setTitle(recent.getTitle());
            board.setContents(recent.getContents());
            board.setImage(recent.getImage());
            boardRepository.save(board);
        }
        if(putDto.getBoardType()==BoardType.REAL) {
            SubRealInDTO rOriginal = modelMapper.map(board.getReals().get(0),SubRealInDTO.class);
            SubRealInDTO rRecent = putDto.getReal();
            if(!rOriginal.getReview().equals(rRecent.getReview())||
                    !rOriginal.getState().equals(rRecent.getState())||
                    !rOriginal.getPMaterial().equals(rRecent.getPMaterial())){
                Real real = modelMapper.map(rRecent,Real.class);
                real.setBoard(board);
                real.setUpdateAt(new Date());
                realRepository.save(real);
            }
        }
        return true;
    }

    /*
        작성자: 홍민석
        작성일: 19-10-22
        내용: 게시물을 삭제합니다.
     */
    @Transactional
    public <Tin extends BoardInDTO> boolean deleteOne(Long seq, Tin inDto) {
        Board board = boardRepository.findBoardByBdSeq(seq);
        //TODO : 권한 인증 코드 작성
        boardRepository.delete(board);
        if(boardRepository.existsById(Integer.parseInt(seq.toString()))) return false;
        return true;
    }
    /*
        작성자: 홍민석
        작성일: 19-10-20
        내용: freethink 게시물 좋아요 기능
        TB_LIKE에 좋아요 기록을 표시한 후,
        게시판 좋아요(good)을 1만큼 증가시킵니다.
    */
    @Transactional
    public boolean setLike(LikeDTO likeDto) {
        Like like = new Like();
        like.setBdSeq(likeDto.getBoardId());
        like.setBoardType(likeDto.getBoardType());
        like.setUser(userRepository.findUserByNickName(likeDto.getNickName()));
        likeRepository.save(like);
        switch(likeDto.getBoardType()){
            case FREE:
                boardRepository.likeFreeByBdSeq(likeDto.getBoardId());
                return true;
            case REPLY:
                replyRepository.likeReplyByRpSeqAndBdSeq(likeDto.getReplyId(),likeDto.getBoardId());
                return true;
            default:
                throw new IdeaInvalidException("Invalid Board Type Exception");
        }
    }

    /*
        작성자: 홍민석
        작성일: 19-10-07
        내용: freethink 게시물 번호를 인자로 받고
        게시물과 관련된 모든 댓글을 반환합니다.
    */
    @Transactional
    public List<ReplyOutDTO> getReplyList(Long bdSeq) {
        return replyRepository.findReplyByBdSeq(bdSeq).stream().map(s->modelMapper.map(s, ReplyOutDTO.class)).collect(Collectors.toList());
    }

    /*
        작성자: 홍민석
        작성일: 19-10-07
        내용: 댓글 작성, bdSeq와 category는 ReplyInDTO 클래스에 존재.
    */
    @Transactional
    public boolean setReply(ReplyInDTO replyInDTO) {
        Reply reply = modelMapper.map(replyInDTO, Reply.class);
        reply.setUser(userRepository.findUserByNickName(replyInDTO.getNickName()));
        reply.setBoard(boardRepository.findBoardByBdSeq(replyInDTO.getSeq()));
        if(replyRepository.save(reply)==null) throw new ReplyNotFoundException();
        return true;
    }

    /*
            작성자: 홍민석
            작성일: 19-10-24
            내용: 글 번호, 댓글 번호를 인자로 전달받아
                해당하는 댓글을 삭제합니다.
    */
    public boolean deleteReply(Long bdSeq, Long replyId) {
        Reply reply = replyRepository.findReplyByRpSeqAndBdSeq(replyId,bdSeq);
        //TODO: 권한 인증 코드 작성
        replyRepository.delete(reply);
        if(replyRepository.existsById(Integer.parseInt(reply.getRpSeq().toString()))){
            return false;
        }
        return true;
    }
    /*
     공통 매퍼
     */
    public <T, E> T convertTo(E source, Class<? extends T> classLiteral) {
        return modelMapper.map(source, classLiteral);
    }
}
