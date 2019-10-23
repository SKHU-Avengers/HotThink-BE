package skhu.ht.hotthink.api.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Category")
@Table(name = "TB_CATEG")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CT_CODE")
    private String code;
    private String category;
}
