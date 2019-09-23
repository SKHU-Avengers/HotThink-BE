package skhu.ht.hotthink.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skhu.ht.hotthink.domain.Free;

import java.util.List;

public interface FreeRepository extends JpaRepository<Free, Integer> {
    List<Free>  findBySeqAndTBCategoryCategory(int seq, String category);


}
