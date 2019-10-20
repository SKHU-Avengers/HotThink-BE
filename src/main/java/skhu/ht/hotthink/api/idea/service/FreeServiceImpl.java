package skhu.ht.hotthink.api.idea.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skhu.ht.hotthink.api.domain.*;
import skhu.ht.hotthink.api.idea.model.*;
import skhu.ht.hotthink.api.idea.repository.CategoryRepository;
import skhu.ht.hotthink.api.idea.repository.FreeLikeRepository;
import skhu.ht.hotthink.api.idea.repository.FreeRepository;
import skhu.ht.hotthink.api.idea.repository.ReplyRepository;
import skhu.ht.hotthink.api.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FreeServiceImpl implements FreeService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    FreeRepository freeRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    FreeLikeRepository freeLikeRepository;
    @Autowired
    ReplyRepository replyRepository;
    @Autowired
    ModelMapper modelMapper = new ModelMapper();

    /*
    작성자: 홍민석
    작성일: 19-10-04
    내용: FreeThink게시판 리스트를 반환합니다.
     */
    @Override
    public List<FreeListDTO> getFreeList(Pagination pagination){
        return freeRepository.findAll(pagination)
                .stream()
                .map(e->modelMapper.map(e,FreeListDTO.class))
                .collect(Collectors.toList());
    }

    /*
    작성자: 홍민석
    작성일: 19-10-04
    내용: 인자값으로 주어지는 게시물번호와 카테고리를 확인한 후
    해당하는 FreeThink게시물을 반환합니다.
     */
    @Override
    @Transactional
    public FreeOutDTO getFree(Long seq){
        //Category categ = categoryRepository.findCategoryByCategory(category);
        //Free free = freeRepository.findFreeBySeqAndCategory(seq, categ);
        Free free = freeRepository.findFreeByFrSeq(seq);
        if(free != null) {
            FreeOutDTO freeOutDto = modelMapper.map(free, FreeOutDTO.class);
            freeOutDto.setLike(free.getGood());
            return freeOutDto;
        }
        return null;
    }

    /*
    작성자: 홍민석
    작성일: 19-10-04
    내용: FreeThink게시물을 작성합니다.
    카테고리별로 게시물번호를 받아오고,
    잘못된 값을 받아올 시 false를 반환합니다.
     */
    @Override
    @Transactional
    public boolean setFree(FreeInDTO freeInDto, String nickname, String category){
        Long seq;
        User user =userRepository.findUserByNickName(nickname);
        if(user != null) {
            Category categ = categoryRepository.findCategoryByCategory(category);
            if(categ != null) {
                Free free = modelMapper.map(freeInDto, Free.class);
                free.setUser(user);
                free.setCategory(categ);
                free.setHits(0);
                if ((seq = freeRepository.findFreeSeq(category)) != -1) {
                    free.setSeq(seq);
                    freeRepository.save(free);
                    return true;
                }
            }
        }
        return false;
    }

    /*
    작성자: 홍민석
    작성일: 19-10-04
    내용: FreeThink게시물을 수정합니다.
     */
    @Override
    @Transactional
    public boolean putFree(Long frSeq, String category, FreeInDTO freeInDto){
        //Category categ = categoryRepository.findCategoryByCategory(category);
        //Free free = freeRepository.findFreeBySeqAndCategory(seq,categ);
        Free free = freeRepository.findFreeByFrSeq(frSeq);
        free.setContents(freeInDto.getContents());
        free.setTitle(freeInDto.getTitle());
        free.setImage(freeInDto.getImage());
        //TODO: 권한 인증 코드 작성
        freeRepository.save(free);
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
    public boolean setFreeLike(Long frSeq, String nickName) {
        FreeLike freeLike = new FreeLike();
        freeLike.setFree(freeRepository.findFreeByFrSeq(frSeq));
        freeLike.setUser(userRepository.findUserByNickName(nickName));
        freeLikeRepository.save(freeLike);
        freeRepository.likeFree(frSeq);
        return true;
    }

    /*
        작성자: 홍민석
        작성일: 19-10-07
        내용: freethink 게시물 번호를 인자로 받고
        게시물과 관련된 모든 댓글을 반환합니다.
    */
    @Override
    @Transactional
    public List<ReplyOutDTO> getReplyList(Long frSeq) {
        return replyRepository.findReplyByFRSEQ(frSeq).stream().map(s->modelMapper.map(s, ReplyOutDTO.class)).collect(Collectors.toList());
    }

    /*
        작성자: 홍민석
        작성일: 19-10-07
        내용: 댓글 작성, frSeq와 category는 ReplyInDTO 클래스에 존재.
    */
    @Override
    @Transactional
    public boolean setReply(ReplyInDTO replyInDTO) {
        Reply reply = modelMapper.map(replyInDTO, Reply.class);
        reply.setUser(userRepository.findUserByNickName(replyInDTO.getNickName()));
        Category categ = categoryRepository.findCategoryByCategory(replyInDTO.getCategory());
        reply.setFree(freeRepository.findFreeByFrSeq(replyInDTO.getFrSeq()));
        if(replyRepository.save(reply)==null) {
            return false;
        }
        return true;
    }

    /*
        작성자: 홍민석
        작성일: 19-10-10
        내용: 프리띵크 삭제.
        성공시 true, 실패시 false를 반환합니다.
    */
    public boolean deleteFree(Long frSeq, FreeInDTO freeInDto) {
        Free free = freeRepository.findFreeByFrSeq(frSeq);
        //TODO: 권한 인증 코드 작성

        freeRepository.delete(free);
        free = freeRepository.findFreeByFrSeq(frSeq);
        if (free != null) {
            return false;
        }
        return true;
    }

    public boolean deleteReply(String freeId, Long replyId) {

        return true;
    }
}
