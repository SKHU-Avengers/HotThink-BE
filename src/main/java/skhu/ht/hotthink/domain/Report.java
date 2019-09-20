package skhu.ht.hotthink.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int seq;

    String reason;
    String detail;

    @ManyToOne
    @JoinColumn(name = "ct_code")
    Category category;

    @ManyToOne
    @JoinColumn(name = "ur_seq")
    User user;

    @ManyToOne
    @JoinColumn(name = "id_seq")
    Idea idea;

    @ManyToOne
    @JoinColumn(name = "fr_seq")
    Free free;

    @ManyToOne
    @JoinColumn(name = "re_seq")
    Reply reply;
}
