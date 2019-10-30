package skhu.ht.hotthink.api.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "EmailConfirm")
@Table(name = "TB_EMAIL_CONFIRM")
public class EmailConfirm {
    @Id
    @Column(name="UR_SEQ")
    private Long seq;

    private Long emailKey;
    @Column(name="REGISTER_AT")
    private Date registerAt;
    @Column(name="EXPIRED_AT")
    private Date expiredAt;

    @MapsId
    @OneToOne
    @JoinColumn(name="UR_SEQ")
    private User user;

}
