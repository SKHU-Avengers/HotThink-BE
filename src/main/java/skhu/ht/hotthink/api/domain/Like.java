package skhu.ht.hotthink.api.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name="TB_LIKE")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="LK_SEQ")
    private Long seq;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="UR_SEQ")
    private User user;
    @Column(name="BD_SEQ")
    private Long bdSeq;
}
