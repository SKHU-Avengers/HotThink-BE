package skhu.ht.hotthink.api.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "TB_REPLY")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="rp_seq")
    private Long rpSeq;
    private Long seq;

    private String adopt;
    private String contents;
    private Date date;
    private Integer good;

    @ManyToOne
    @JoinColumn(name = "fr_seq")
    private Free free;

    @ManyToOne
    @JoinColumn(name = "re_seq")
    private Report report;

    @ManyToOne
    @JoinColumn(name = "ur_seq")
    private User user;
}
