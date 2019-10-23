package skhu.ht.hotthink.api.idea.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skhu.ht.hotthink.api.MessageState;
import skhu.ht.hotthink.api.domain.*;
import skhu.ht.hotthink.api.idea.model.*;
import skhu.ht.hotthink.api.idea.repository.*;
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
    ModelMapper modelMapper = new ModelMapper();

    /*
        작성자: 홍민석
        작성일: 19-10-04
        내용: RealThink게시판 리스트를 반환합니다.
    */
    @Transactional
    public <Tlist> List<Tlist> getBoardList(IdeaPagination pagination,Class<? extends Tlist> classLiteral) {
        List<Board> mapper;
        Category category = categoryRepository.findCategoryByCategory(pagination.getCategory());
        return (List<Tlist>)boardRepository.findAll(pagination)
                .stream()
                .map(s -> convertTo(s,classLiteral))
                .collect(Collectors.toList());
    }

    /*
        작성자: 홍민석
        작성일: 19-10-04
        내용: Free, HotThink게시판 리스트를 반환합니다.
    */
    @Transactional
    public <Tlist> List<Tlist> getBoardList(Pagination pagination,Class<? extends Tlist> classLiteral) {
        return boardRepository.findAll(pagination)
                .stream()
                .map(e->convertTo(e,classLiteral))
                .collect(Collectors.toList());
    }

    /*
        작성자: 홍민석
        작성일: 19-10-04
        내용: 인자값으로 주어지는 게시물번호와 카테고리를 확인한 후
        해당하는 RealThink게시물을 반환합니다.
    */
    @Transactional
    public <Tout> Tout getOne(Long seq, Class<? extends Tout> classLiteral){
        Board board = boardRepository.findBoardByBdSeq(seq);
        return modelMapper.map(board, classLiteral);
    }

    @Transactional
    public <Tin> MessageState setOne(Tin inDto, String nickname, String category, String boardType) {
        Long seq;
        Board board = modelMapper.map(inDto, Board.class);
        board.setCategory(categoryRepository.findCategoryByCategory(category));
        if ((seq = boardRepository.findBoardSeq(category)) != -1) {
            board.setSeq(seq);
            board.setUser(userRepository.findUserByNickName(nickname));
            if(board.getUser()!=null) {
                board.setBoardType(boardType);
                board.setHits(0);
                board.setGood(0);
                board.setCreateAt(new Date());
                if (boardRepository.save(board) != null) {
                    return MessageState.Created;
                }
            }else{
                 return MessageState.NotExist;
            }
        }
        return MessageState.Fail;
    }

    public <Tin> MessageState putOne(Long seq, String category, Tin inDto) {
            Board board = modelMapper.map(inDto, Board.class);

            if(boardRepository.save(board)==null){
                return MessageState.Fail;
            }
            return MessageState.Success;
    }

    @Transactional
    public <Tin> MessageState deleteOne(Long seq, Tin inDto) {
        Board board = boardRepository.findBoardByBdSeq(seq);
        //TODO : 권한 인증 코드 작성
        boardRepository.delete(board);

        if(boardRepository.existsById(Integer.parseInt(seq.toString()))){
            return MessageState.Fail;
        }
        return MessageState.Success;
    }
    /*
        작성자: 홍민석
        작성일: 19-10-20
        내용: freethink 게시물 좋아요 기능
        TB_LIKE에 좋아요 기록을 표시한 후,
        게시판 좋아요(good)을 1만큼 증가시킵니다.
    */
    @Transactional
    public MessageState setFreeLike(Long bdSeq, String nickName) {
        Like like = new Like();
        like.setBdSeq(bdSeq);
        like.setUser(userRepository.findUserByNickName(nickName));
        likeRepository.save(like);

        //freeRepository.likeFree(bdSeq); TODO:기능추가 해야함
        return MessageState.Success;
    }

    /*
        작성자: 홍민석
        작성일: 19-10-07
        내용: freethink 게시물 번호를 인자로 받고
        게시물과 관련된 모든 댓글을 반환합니다.
    */
    @Transactional
    public List<ReplyOutDTO> getReplyList(Long bdSeq) {
        return replyRepository.findReplyByBDSEQ(bdSeq).stream().map(s->modelMapper.map(s, ReplyOutDTO.class)).collect(Collectors.toList());
    }

    /*
        작성자: 홍민석
        작성일: 19-10-07
        내용: 댓글 작성, bdSeq와 category는 ReplyInDTO 클래스에 존재.
    */
    @Transactional
    public MessageState setReply(ReplyInDTO replyInDTO) {
        Reply reply = modelMapper.map(replyInDTO, Reply.class);
        reply.setUser(userRepository.findUserByNickName(replyInDTO.getNickName()));
        reply.setBoard(boardRepository.findBoardByBdSeq(replyInDTO.getSeq()));
        if(replyRepository.save(reply)==null) {
            return MessageState.Error;
        }
        return MessageState.Created;
    }

    /*
            작성자: 홍민석
            작성일: 19-10-10
            내용: 댓글 삭제.
            성공시 true, 실패시 false를 반환합니다.
    */
    public MessageState deleteReply(String bdSeq, Long replyId) {
        //TODO: 권한 인증 코드 작성

        return MessageState.Success;
    }
    /*
     * 공통 매퍼
     */
    public <T, E> T convertTo(E source, Class<? extends T> classLiteral) {
        return modelMapper.map(source, classLiteral);
    }
}
