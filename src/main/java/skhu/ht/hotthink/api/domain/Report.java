package skhu.ht.hotthink.api.domain;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity(name="TB_REPORT")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    private String reason;
    private String detail;

    @ManyToOne
    @JoinColumn(name = "ct_code")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "ur_seq")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_seq")
    private Idea idea;

    @ManyToOne
    @JoinColumn(name = "fr_seq")
    private Free free;

    @ManyToOne
    @JoinColumn(name = "re_seq")
    private Reply reply;
}
