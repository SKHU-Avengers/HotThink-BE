package skhu.ht.hotthink.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import skhu.ht.hotthink.api.domain.enums.IdeaState;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity(name="Real")
@Table(name="TB_REAL")
public class Real {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="RL_SEQ")
    private Long seq;

    private IdeaState state;
    @Column(name="UPDATE_AT")
    private Date updateAt;
    private String difference;
    private String pMaterial;
    private String inventor;
    @Column(name="RIGHT_HOLDER")
    private String rightHolder;
    @Column(name="PROGRESS_RATE")
    private Integer progressRate;
    @OneToMany
    @JoinColumn(name="RL_SEQ")
    private List<Attach> attaches;

    @ManyToOne
    @JoinColumn(name = "BD_SEQ")
    private Board board;
}
