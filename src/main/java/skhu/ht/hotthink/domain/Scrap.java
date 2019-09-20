package skhu.ht.hotthink.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Scrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int seq;

    @ManyToOne
    @JoinColumn(name = "ur_seq")
    User user;

    @ManyToOne
    @JoinColumn(name = "fr_seq")
    Free free;

    @ManyToOne
    @JoinColumn(name = "id_seq")
    Idea idea;

    @ManyToOne
    @JoinColumn(name = "code")
    Category category;
}
