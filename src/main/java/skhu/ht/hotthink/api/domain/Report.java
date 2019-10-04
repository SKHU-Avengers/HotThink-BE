package skhu.ht.hotthink.api.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity(name="TB_REPORT")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long seq;

    @Getter @Setter private String reason;
    @Getter @Setter private String detail;

    @ManyToOne
    @JoinColumn(name = "ct_code")
    @Getter @Setter private Category category;

    @ManyToOne
    @JoinColumn(name = "ur_seq")
    @Getter @Setter private User user;

    @ManyToOne
    @JoinColumn(name = "id_seq")
    @Getter @Setter private Idea idea;

    @ManyToOne
    @JoinColumn(name = "fr_seq")
    @Getter @Setter private Free free;

    @ManyToOne
    @JoinColumn(name = "re_seq")
    @Getter @Setter private Reply reply;
}
