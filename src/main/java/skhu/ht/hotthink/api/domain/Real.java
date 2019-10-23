package skhu.ht.hotthink.api.domain;

import javax.persistence.*;
import java.util.Date;

@Entity(name="Real")
@Table(name="TB_REAL")
public class Real {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="RL_SEQ")
    private Long seq;

    private IdeaState state;
    private Integer myScore;
    private Integer sellerScore;

    private Date updateAt;
    private String review;
    private String pMaterial;
}
