package skhu.ht.hotthink.api.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity(name="TB_SCRAP")
public class Scrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "UR_SEQ")
    private User user;

    @ManyToOne
    @JoinColumn(name = "FR_SEQ")
    private Free free;

    @ManyToOne
    @JoinColumn(name = "ID_SEQ")
    private Idea idea;

    @ManyToOne
    @JoinColumn(name = "CODE")
    private Category category;
}
