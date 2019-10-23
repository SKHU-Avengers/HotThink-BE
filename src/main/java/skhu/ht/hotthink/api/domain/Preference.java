package skhu.ht.hotthink.api.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name="TB_PREFERENCE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Preference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="FR_SEQ")
    private Long seq;
    private String preference;

    @ManyToOne
    @JoinColumn(name="UR_SEQ")
    private User user;

    public Preference(String preference){
        this.preference = preference;
    }
}
