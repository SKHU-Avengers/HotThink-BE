package skhu.ht.hotthink.api.idea.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skhu.ht.hotthink.api.domain.Category;
import skhu.ht.hotthink.api.domain.Idea;
import skhu.ht.hotthink.api.idea.model.*;
import skhu.ht.hotthink.api.idea.repository.CategoryRepository;
import skhu.ht.hotthink.api.idea.repository.IdeaRepository;
import skhu.ht.hotthink.api.user.repository.UserRepository;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IdeaServiceImpl<Tin,Tout,Tlist> implements IdeaService{

    @Autowired
    IdeaRepository ideaRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper modelMapper = new ModelMapper();

    /*
        작성자: 홍민석
        작성일: 19-10-04
        내용: RealThink게시판 리스트를 반환합니다.
    */
    @Transactional
    public List<Tlist> getIdeaList(IdeaPagination pagination) {
        List<Idea> mapper;
        if(pagination.getType().name().equals("HOT")) {
            Category category = categoryRepository.findCategoryByCategory(pagination.getCategory());
            return (List<Tlist>)ideaRepository.findAll(pagination, category)
                    .stream()
                    .map(s -> modelMapper.map(s, HotListDTO.class))
                    .collect(Collectors.toList());
        }else{
            Category category = categoryRepository.findCategoryByCategory(pagination.getCategory());
            return (List<Tlist>)ideaRepository.findAll(pagination, category)
                    .stream()
                    .map(s -> modelMapper.map(s, RealListDTO.class))
                    .collect(Collectors.toList());
        }
    }

    /*
        작성자: 홍민석
        작성일: 19-10-04
        내용: 인자값으로 주어지는 게시물번호와 카테고리를 확인한 후
        해당하는 RealThink게시물을 반환합니다.
    */
    @Transactional
    public Tout getIdea(Long seq){
        Tout returnType;
        Idea idea = ideaRepository.findIdeaByIdSeq(seq);
        if(idea.getState().equals("HOT")){
            returnType=modelMapper.map(idea, (Type) HotOutDTO.class);
        }
        else{
            returnType=modelMapper.map(idea, (Type) RealOutDTO.class);
        }
        return returnType;
    }
    @Transactional
    public boolean setIdea(Tin inDto, String nickname, String category) {
        Long seq;
        Idea idea = modelMapper.map(inDto, Idea.class);
        idea.setCategory(categoryRepository.findCategoryByCategory(category));
        if ((seq = ideaRepository.findIdeaSeq(category)) != -1) {
            idea.setSeq(seq);
            idea.setUser(userRepository.findUserByNickName(nickname));
            if(idea.getUser()!=null) {
                idea.setHits(0);
                idea.setCreateAt(new Date());
                if (ideaRepository.save(idea) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean putIdea(Long realId, String category, Tin inDto) {
        Idea idea = modelMapper.map(inDto, Idea.class);

        if(ideaRepository.save(idea)==null){
            return false;
        }
        return true;
    }

    @Transactional
    public boolean deleteIdea(Long realId, Tin inDto) {
        Idea idea = ideaRepository.findIdeaByIdSeq(realId);
        //TODO : 권한 인증 코드 작성
        ideaRepository.delete(idea);

        return ideaRepository.existsById(Integer.parseInt(realId.toString()));
    }
}
