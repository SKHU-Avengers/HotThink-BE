package skhu.ht.hotthink.api.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Attach")
@Table(name = "TB_ATTACH")
public class Attach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="AC_SEQ")
    private Long seq;
    private String path;
}
