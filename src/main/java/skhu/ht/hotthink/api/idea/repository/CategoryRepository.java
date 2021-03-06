package skhu.ht.hotthink.api.idea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skhu.ht.hotthink.api.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findCategoryByCategory(String category);
}
