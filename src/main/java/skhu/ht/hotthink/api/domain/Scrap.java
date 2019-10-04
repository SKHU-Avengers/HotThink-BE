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
    @Getter @Setter private Long seq;

    @ManyToOne
    @JoinColumn(name = "UR_SEQ")
    @Getter @Setter private User user;

    @ManyToOne
    @JoinColumn(name = "FR_SEQ")
    @Getter @Setter private Free free;

    @ManyToOne
    @JoinColumn(name = "ID_SEQ")
    @Getter @Setter private Idea idea;

    @ManyToOne
    @JoinColumn(name = "CODE")
    @Getter @Setter private Category category;
}
