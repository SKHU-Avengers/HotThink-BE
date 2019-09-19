package skhu.ht.hotthink.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Free {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int seq;

    @ManyToOne
    @JoinColumn(name = "CT_CODE")
    Category category;
    int hits;
    String head;
    Date date;
    String contents;
    int like;

    @OneToMany
    @JoinColumn(name = "UR_SEQ")
    User user;
    String image;
}
