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
    @Getter @Setter private Long seq;

    @Getter @Setter private String contents;
    @Getter @Setter private Date date;
    @Getter @Setter private int good;
    @Getter @Setter private String adopt;

    @ManyToOne
    @JoinColumn(name = "fr_seq")
    @Getter @Setter private Free free;

    @ManyToOne
    @JoinColumn(name = "re_seq")
    @Getter @Setter private Report report;

    @ManyToOne
    @JoinColumn(name = "ur_seq")
    @Getter @Setter private User user;
}
