package skhu.ht.hotthink.api.idea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skhu.ht.hotthink.api.domain.Attach;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface AttachRepository extends JpaRepository<Attach,Integer> {
    default List<Attach> saveList(List<Attach> attaches){
        List<Attach> after = new ArrayList<>();
        for(Attach attach : attaches){
            after.add(save(attach));
        }
        return after;
    }
}
