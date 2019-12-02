package skhu.ht.hotthink.api.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TB_CONVERSATION")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CV_SEQ")
    protected Long seq;
    protected Date at;
    protected String msg;
    @ManyToOne
    @JoinColumn(name="UR_FROM")
    protected User from;
    @ManyToOne
    @JoinColumn(name="UR_TO")
    protected User to;
}
