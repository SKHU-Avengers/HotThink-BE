package skhu.ht.hotthink.api.domain;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity(name="Follow")
@Table(name="TB_FOLLOW")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="FL_SEQ")
    private int seq;

    @ManyToOne
    @JoinColumn(name = "UR_CELEBRITY")
    private User celebrity;

    @ManyToOne
    @JoinColumn(name = "UR_FOLLOWER")
    private User follower;
}
