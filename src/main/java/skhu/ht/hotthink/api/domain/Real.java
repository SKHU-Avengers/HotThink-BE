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
    private Date updateAt;
    private String review;
    private String pMaterial;

    @ManyToOne
    @JoinColumn(name = "BD_SEQ")
    private Board board;
}
