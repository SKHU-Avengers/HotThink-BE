package skhu.ht.hotthink.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skhu.ht.hotthink.api.domain.Preference;
@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Integer> {


}
