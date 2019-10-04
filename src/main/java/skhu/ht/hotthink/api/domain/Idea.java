package skhu.ht.hotthink.api.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import skhu.ht.hotthink.api.domain.Category;
import skhu.ht.hotthink.api.domain.User;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "TB_IDEA")
public class Idea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id_seq;
    @Getter @Setter private Long seq;
    @Getter @Setter private int hits;
    @Getter @Setter private String title;
    @Getter @Setter private String state;
    @Getter @Setter private int myScore;
    @Getter @Setter private int sellerScore;
    @Getter @Setter private Date date;
    @Getter @Setter private String contents;
    @Getter @Setter private String review;
    @Getter @Setter private String pmaterial;

    @ManyToOne
    @JoinColumn(name = "code")
    @Getter @Setter private Category category;
    @ManyToOne
    @JoinColumn(name = "ur_seq")
    @Getter @Setter private User user;
}
