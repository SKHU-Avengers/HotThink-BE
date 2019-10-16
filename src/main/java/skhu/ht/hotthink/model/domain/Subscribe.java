package skhu.ht.hotthink.model.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int seq;

    @ManyToOne
    @JoinColumn(name = "ur_star")
    User star;

    @ManyToOne
    @JoinColumn(name = "ur_subscriber")
    User subscriber;
}
