package skhu.ht.hotthink.api.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name="TB_LIKE")
public class FreeLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="UR_SEQ")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="FR_SEQ")
    private Free free;
}
