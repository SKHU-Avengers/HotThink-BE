package skhu.ht.hotthink.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Idea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int seq;

    @ManyToOne
    @JoinColumn(name = "code")
    Category category;
    int hits;
    String head;
    String state;
    int myScore;
    int sellerScore;
    Date date;
    String contents;
    String review;

    @ManyToOne
    @JoinColumn(name = "ur_seq")
    User user;

    String pmaterial;
}
