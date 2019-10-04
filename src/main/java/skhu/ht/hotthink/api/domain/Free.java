package skhu.ht.hotthink.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "TB_FREE")
public class Free {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long fr_seq;
    @Getter @Setter private Long seq;
    @Getter @Setter private int hits;
    @Getter @Setter private String title;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Getter @Setter private Date date;
    @Getter @Setter private String contents;
    @Getter @Setter private int good;
    @Getter @Setter private String image;
    @ManyToOne
    @JoinColumn(name = "ur_seq")
    @Getter @Setter private User user;

    @ManyToOne
    @JoinColumn(name = "CODE")
    @Getter @Setter private Category category;
}
