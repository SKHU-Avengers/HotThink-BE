package skhu.ht.hotthink.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity(name="TB_PREFERENCE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@EqualsAndHashCode(of = "preference")
@ToString(of = "preference")
public class Preference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="FR_SEQ")
    private Long seq;

    @NonNull
    @Getter
    private String preference;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="UR_SEQ")
    @Setter
    private User user;

//    public Preference(String preference, User user){
//        this.preference=preference;
//        this.user=user;
//    }
}
