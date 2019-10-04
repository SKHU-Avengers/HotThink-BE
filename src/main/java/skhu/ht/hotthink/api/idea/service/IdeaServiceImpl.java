package skhu.ht.hotthink.api.idea.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skhu.ht.hotthink.api.domain.Category;
import skhu.ht.hotthink.api.domain.Free;
import skhu.ht.hotthink.api.domain.Idea;
import skhu.ht.hotthink.api.domain.User;
import skhu.ht.hotthink.api.idea.model.FreeInDTO;
import skhu.ht.hotthink.api.idea.model.FreeListDTO;
import skhu.ht.hotthink.api.idea.model.FreeOutDTO;
import skhu.ht.hotthink.api.idea.model.Pagination;
import skhu.ht.hotthink.api.idea.repository.CategoryRepository;
import skhu.ht.hotthink.api.idea.repository.FreeRepository;
import skhu.ht.hotthink.api.idea.repository.IdeaRepository;
import skhu.ht.hotthink.api.mapper.DomainMapper;
import skhu.ht.hotthink.api.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collector;
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
    DomainMapper domainMapper;

    /*
    작성자: 홍민석
    작성일: 19-10-04
    내용: FreeThink게시판 리스트를 반환합니다.
     */
    @Override
    public List<FreeListDTO> getFreeList(Pagination pagination){
        return freeRepository.findAll(pagination).stream().map(e->domainMapper.convertToDomain(e,FreeListDTO.class)).collect(Collectors.toList());
    }

    /*
    작성자: 홍민석
    작성일: 19-10-04
    내용: 인자값으로 주어지는 게시물번호와 카테고리를 확인한 후
    해당하는 FreeThink게시물을 반환합니다.
     */
    @Override
    public FreeOutDTO getFree(int seq, String category){
        return domainMapper.convertToDomain(freeRepository.findFreeBySeqAndCategory(seq, category),FreeOutDTO.class);
    }

    /*
    작성자: 홍민석
    작성일: 19-10-04
    내용: FreeThink게시물을 작성합니다.
     */
    @Override
    public boolean setFree(FreeInDTO freeInDto, String nickname, String category){
        User user =userRepository.findUserByNickName(nickname);
        Category categ = categoryRepository.findCategoryByCategory(category);
        Free free = domainMapper.convertToDomain(freeInDto,Free.class);
        free.setUser(user);
        free.setCategory(categ);
        freeRepository.save(domainMapper.convertToDomain(freeInDto,Free.class));
        return true;
    }

    /*
    작성자: 홍민석
    작성일: 19-10-04
    내용: FreeThink게시물을 수정합니다.
     */
    @Override
    public boolean putFree(int seq, String category, Free free, String writer){
        if(freeRepository.findFreeBySeqAndCategory(seq,category).getUser().equals(writer)) {
            freeRepository.save(free);
            return true;
        }
        return false;
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
