package skhu.ht.hotthink.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Where;
import org.springframework.security.core.parameters.P;
import skhu.ht.hotthink.api.domain.enums.BoardType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity(name="Board")
@Table(name = "TB_BOARD")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BD_SEQ")
    private Long bdSeq;
    private Long seq;
    private Integer hits;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name="CREATE_AT")
    private Date createAt;

    private String title;
    private String contents;

    @Column(name="BOARD_TYPE")
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @Column(name="THUMBNAIL_IMG")
    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UR_SEQ")
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CT_CODE")
    private Category category;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "RL_SEQ")
    private List<Real> reals;

    @OneToMany(mappedBy = "board")
    private List<History> histories;

    @OneToMany(mappedBy = "board")
    private List<Reply> replies;

    @OneToMany(mappedBy = "board")
    private List<Scrap> scraps;

    @OneToMany(mappedBy="bdSeq")
    @Where(clause = "BOARD_TYPE='FREE'")
    private List<Like> likes;
}
