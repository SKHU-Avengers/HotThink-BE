package skhu.ht.hotthink.api.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "TB_REPLY")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="RP_SEQ")
    private Long rpSeq;
    private Long seq;

    private String adopt;
    private String contents;
    private Date date;
    private Integer good;

    @ManyToOne
    @JoinColumn(name = "BD_SEQ")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "RE_SEQ")
    private Report report;

    @ManyToOne
    @JoinColumn(name = "UR_SEQ")
    private User user;
}
