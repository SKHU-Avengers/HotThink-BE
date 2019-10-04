package skhu.ht.hotthink.api.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
public class Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private int seq;

    @ManyToOne
    @JoinColumn(name = "UR_STAR")
    @Getter @Setter private User star;

    @ManyToOne
    @JoinColumn(name = "UR_SUBSCRIBER")
    @Getter @Setter private User subscriber;
}
