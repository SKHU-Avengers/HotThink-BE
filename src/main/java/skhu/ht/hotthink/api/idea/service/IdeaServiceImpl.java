package skhu.ht.hotthink.api.idea.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skhu.ht.hotthink.api.domain.Category;
import skhu.ht.hotthink.api.domain.Free;
import skhu.ht.hotthink.api.domain.Idea;
import skhu.ht.hotthink.api.domain.User;
import skhu.ht.hotthink.api.idea.model.*;
import skhu.ht.hotthink.api.idea.repository.CategoryRepository;
import skhu.ht.hotthink.api.idea.repository.FreeRepository;
import skhu.ht.hotthink.api.idea.repository.IdeaRepository;
import skhu.ht.hotthink.api.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IdeaServiceImpl implements IdeaService{

    @Autowired
    IdeaRepository ideaRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FreeRepository freeRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper modelMapper = new ModelMapper();

    /*
    작성자: 홍민석
    작성일: 19-10-04
    내용: FreeThink게시판 리스트를 반환합니다.
     */
    @Override
    public List<FreeListDTO> getFreeList(Pagination pagination){
        return freeRepository.findAll(pagination).stream().map(e->modelMapper.map(e,FreeListDTO.class)).collect(Collectors.toList());
    }

    /*
    작성자: 홍민석
    작성일: 19-10-04
    내용: 인자값으로 주어지는 게시물번호와 카테고리를 확인한 후
    해당하는 FreeThink게시물을 반환합니다.
     */
    @Override
    public FreeOutDTO getFree(Long seq, String category){
        Category categ = categoryRepository.findCategoryByCategory(category);
        Free free = freeRepository.findFreeBySeqAndCategory(seq, categ);
        FreeOutDTO freeOutDto = modelMapper.map(free,FreeOutDTO.class);
        return freeOutDto;
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
        Category categ = categoryRepository.findCategoryByCategory(category);
        Free free = modelMapper.map(freeInDto,Free.class);
        free.setUser(user);
        free.setCategory(categ);
        if((seq = freeRepository.findFreeSeq(category)) == -1) {
            return false;
        }
        free.setSeq(seq);
        freeRepository.save(free);
        return true;
    }

    /*
    작성자: 홍민석
    작성일: 19-10-04
    내용: FreeThink게시물을 수정합니다.
     */
    @Override
    @Transactional
    public boolean putFree(Long seq, String category, FreeInDTO freeInDto){
        Category categ = categoryRepository.findCategoryByCategory(category);
        Free free = freeRepository.findFreeBySeqAndCategory(seq,categ);
        free.setContents(freeInDto.getContents());
        free.setTitle(freeInDto.getTitle());
        free.setImage(freeInDto.getImage());
        //TODO: 권한 인증 코드 작성
        freeRepository.save(free);
        return true;
    }
    /*
        작성자: 홍민석
        작성일: 19-10-04
        내용: RealThink게시판 리스트를 반환합니다.
    */
    @Override
    public List<Idea> getRealList(Pagination pagination) {
        return ideaRepository.findAll(pagination);
    }

    /*
        작성자: 홍민석
        작성일: 19-10-04
        내용: 인자값으로 주어지는 게시물번호와 카테고리를 확인한 후
        해당하는 FreeThink게시물을 반환합니다.
    */
    @Override
    public Idea getReal(int seq, String category) {

        return ideaRepository.findIdeaBySeqAndCategory(seq,category);
    }
}
