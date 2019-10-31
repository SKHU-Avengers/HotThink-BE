package skhu.ht.hotthink.api.domain;


import lombok.Data;
import skhu.ht.hotthink.api.domain.enums.SubscribeType;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "Pay")
@Table(name = "TB_PAY")
public class Pay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UR_SEQ")
    private Long seq;

    private Date start;
    private Date end;

    @Column(name = "SUBSCRIBE_TYPE")
    private SubscribeType subscribeType;

    @MapsId
    @OneToOne
    @JoinColumn(name="UR_SEQ")
    private User user;
}
