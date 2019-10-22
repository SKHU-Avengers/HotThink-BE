package skhu.ht.hotthink.api.domain;

import javax.persistence.*;

@Entity(name="TB_RP_LIKE")
public class ReplyLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    @JoinColumn(name="RE_SEQ")
    private Long reSeq;
    @JoinColumn(name="UR_SEQ")
    private Long urSeq;
}
