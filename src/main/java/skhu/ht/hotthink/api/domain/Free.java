package skhu.ht.hotthink.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@ToString(exclude={"Reply"})
@EqualsAndHashCode(exclude={"Reply"})
@Entity(name = "TB_FREE")
public class Free {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="FR_SEQ")
    private Long frSeq;
    private Long seq;
    private Integer hits;
    private String title;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createAt;
    private String contents;
    private Integer good;
    @Column(name="thumbnailImg")
    private String image;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UR_SEQ")
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CT_CODE")
    private Category category;
/*
    @JsonIgnore
    @OneToMany(mappedBy = "SEQ")
    List<Reply> replies;
 */
}
