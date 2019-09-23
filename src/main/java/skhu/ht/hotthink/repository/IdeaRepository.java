package skhu.ht.hotthink.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skhu.ht.hotthink.domain.Idea;

import java.util.List;

public interface IdeaRepository extends JpaRepository<Idea, Integer> {
    List<Idea>  findBySeqAndTBCategoryCategory(int seq, String category);
}
