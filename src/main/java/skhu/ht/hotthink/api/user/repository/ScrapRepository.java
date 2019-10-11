package skhu.ht.hotthink.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skhu.ht.hotthink.api.domain.Scrap;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap,Integer> {
}
