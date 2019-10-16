package skhu.ht.hotthink.model.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int seq;

    String contents;
    Date date;
    int good;
    String adopt;

    @ManyToOne
    @JoinColumn(name = "fr_seq")
    Free free;

    @ManyToOne
    @JoinColumn(name = "re_seq")
    Report report;

    @ManyToOne
    @JoinColumn(name = "ur_seq")
    User user;
}
