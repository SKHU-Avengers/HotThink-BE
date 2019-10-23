package skhu.ht.hotthink.api.domain;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity(name = "Report")
@Table(name="TB_REPORT")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    private String reason;
    private String detail;

    @ManyToOne
    @JoinColumn(name = "CT_CODE")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "UR_SEQ")
    private User user;

    @ManyToOne
    @JoinColumn(name = "BD_SEQ")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "RE_SEQ")
    private Reply reply;
}
