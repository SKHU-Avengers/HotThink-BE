package skhu.ht.hotthink.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.Where;
import skhu.ht.hotthink.api.domain.enums.BoardType;
import skhu.ht.hotthink.api.domain.enums.UseAt;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Entity(name="Board")
@Table(name = "TB_BOARD")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BD_SEQ")
    private Long bdSeq;
    private Long seq;
    @Setter
    private Integer hits;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name="CREATE_AT")
    private Date createAt;
    private String title;
    private String contents;

    @Setter
    @Column(name="USE_AT")
    @Enumerated(EnumType.STRING)
    private UseAt useAt;

    @Setter
    @Column(name="BOARD_TYPE")
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    //@Column(name="THUMBNAIL_IMG")
    //private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UR_SEQ")
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CT_CODE")
    private Category category;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Real> reals;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<History> histories;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @Where(clause = "SUPER_SEQ IS NULL")
    private List<Reply> replies;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Scrap> scraps;

    @OneToMany(mappedBy="bdSeq", cascade = CascadeType.ALL, orphanRemoval = true)
    @Where(clause = "BOARD_TYPE='FREE'")
    private List<Like> likes;

    @OneToMany(mappedBy="boardSeq", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Where(clause = "BOARD_REFERENCE_TYPE='BOARD'")
    private List<Attach> attaches;

    public void setTitleAndContents(String title, String contents){
        this.title = title;
        this.contents = contents;
    }

    @Builder(builderClassName = "FreeHotBuilder", builderMethodName = "FreeHotBuilder")
    public Board(Long seq, Date createAt, String title, String contents, BoardType boardType, User user, Category category) {
        this.seq = seq;
        this.hits = 0;
        this.createAt = createAt;
        this.title = title;
        this.contents = contents;
        this.boardType = boardType;
        this.user = user;
        this.category = category;
    }
}
