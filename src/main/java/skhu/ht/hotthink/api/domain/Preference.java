package skhu.ht.hotthink.api.domain;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity(name="TB_PREFERENCE")
public class Preference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    private String preference;

    @ManyToOne
    @JoinColumn(name="UR_SEQ")
    private User user;
}
