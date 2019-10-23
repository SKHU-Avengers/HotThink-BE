package skhu.ht.hotthink.api.domain;

import lombok.*;

import javax.persistence.*;

@Entity(name="TB_PREFERENCE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "preference")
public class Preference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="FR_SEQ")
    private Long seq;

    @NonNull
    @Getter
    private String preference;

    @ManyToOne
    @JoinColumn(name="UR_SEQ")
    private User user;

    public Preference(String preference, User user){
        this.preference=preference;
        this.user=user;
    }
}
