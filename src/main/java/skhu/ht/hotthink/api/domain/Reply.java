package skhu.ht.hotthink.api.domain;

import lombok.Data;
import skhu.ht.hotthink.api.domain.enums.ReplyAdopt;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "Reply")
@Table(name = "TB_REPLY")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="RP_SEQ")
    private Long rpSeq;

    @Enumerated(EnumType.STRING)
    private ReplyAdopt adopt;
    private String contents;

    private Date at;
    private Integer good;

    @ManyToOne
    @JoinColumn(name = "BD_SEQ")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "SUB_RP_SEQ")
    private Reply reply;

    @ManyToOne
    @JoinColumn(name = "UR_SEQ")
    private User user;
}
