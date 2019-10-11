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

import java.util.List;

@Service
public class IdeaServiceImpl implements IdeaService{

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
    public List<Idea> getIdeaList(Pagination pagination) {
        Category category = categoryRepository.findCategoryByCategory(pagination.getCategory());
        return ideaRepository.findAll(pagination,category);
    }

    /*
        작성자: 홍민석
        작성일: 19-10-04
        내용: 인자값으로 주어지는 게시물번호와 카테고리를 확인한 후
        해당하는 FreeThink게시물을 반환합니다.
    */
    @Transactional
    public Idea getIdea(int seq, String category) {
        Category categ = categoryRepository.findCategoryByCategory(category);
        return ideaRepository.findIdeaBySeqAndCategory(seq,categ);
    }
}
