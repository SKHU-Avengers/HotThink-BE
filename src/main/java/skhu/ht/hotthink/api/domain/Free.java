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
    private Long fr_seq;
    private Long seq;
    private int hits;
    private String title;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;
    private String contents;
    private int good;
    private String image;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UR_SEQ")
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CT_CODE")
    private Category category;
}
