package skhu.ht.hotthink.api.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="TB_PREFERENCE")
public class Preference {
    public Preference(String name){
        this.preference=name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    private String preference;

    @ManyToOne
    @JoinColumn(name="UR_SEQ")
    private User user;

}
