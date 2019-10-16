package skhu.ht.hotthink.model.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity()
public class Free {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int seq;

    @ManyToOne
    @JoinColumn(name = "CODE")
    Category category;

    int hits;
    String title;
    Date date;
    String contents;
    int good;

    @ManyToOne
    @JoinColumn(name = "ur_seq")
    User user;

    String image;
}
