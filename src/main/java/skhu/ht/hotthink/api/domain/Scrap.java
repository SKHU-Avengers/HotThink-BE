package skhu.ht.hotthink.api.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Data
@Entity(name="Scrap")
@Table(name="TB_SCRAP")
public class Scrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SC_SEQ")
    private Long seq;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "UR_SEQ")
    private User user;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "BD_SEQ")
    private Board board;
}
