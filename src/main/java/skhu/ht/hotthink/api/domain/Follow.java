package skhu.ht.hotthink.api.domain;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity(name="TB_FOLLOW")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @ManyToOne
    @JoinColumn(name = "UR_CELEBRITY")
    private User celebrity;

    @ManyToOne
    @JoinColumn(name = "UR_FOLLOWER")
    private User follower;
}
