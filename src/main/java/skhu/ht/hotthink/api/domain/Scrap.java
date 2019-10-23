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
    @Column(name="SC_SEQ")
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "UR_SEQ")
    private User user;

    @ManyToOne
    @JoinColumn(name = "BD_SEQ")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "CODE")
    private Category category;
}
