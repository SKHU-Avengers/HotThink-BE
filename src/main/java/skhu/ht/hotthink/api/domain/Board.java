package skhu.ht.hotthink.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "TB_BOARD")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BD_SEQ")
    private Long bdSeq;
    private Long seq;
    private Integer hits;
    private Integer good;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name="CREATE_AT")
    private Date createAt;

    private String title;
    private String contents;

    @Column(name="BOARD_TYPE")
    private String boardType;

    @Column(name="thumbnailImg")
    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UR_SEQ")
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CT_CODE")
    private Category category;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RL_SEQ")
    private Real real;

    @OneToMany(mappedBy = "board")
    private List<History> histories;
}
