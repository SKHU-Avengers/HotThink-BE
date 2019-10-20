package skhu.ht.hotthink.api.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name = "TB_CATEG")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String code;
    private String category;
}
