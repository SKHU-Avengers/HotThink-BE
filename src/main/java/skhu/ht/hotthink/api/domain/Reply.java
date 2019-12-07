package skhu.ht.hotthink.api.domain;

import lombok.*;
import org.hibernate.annotations.Where;
import skhu.ht.hotthink.api.domain.enums.ReplyAdopt;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Entity(name = "Reply")
@Table(name = "TB_REPLY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="RP_SEQ")
    private Long rpSeq;

    @Enumerated(EnumType.STRING)
    private ReplyAdopt adopt;
    @Setter
    private String contents;
    @NonNull
    private Date at;
    @ManyToOne
    @JoinColumn(name = "BD_SEQ")
    private Board board;

    @Column(name = "DEPTH")
    private Integer depth;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="SUPER_SEQ")
    private List<Reply> subReplies;

    @Column(name = "SUPER_SEQ")
    private Long superSeq;

    @OneToMany(mappedBy="bdSeq")
    @Where(clause = "BOARD_TYPE='FREE'")
    private List<Like> likes;

    @ManyToOne
    @JoinColumn(name = "UR_SEQ")
    private User user;

    @Builder(builderClassName="BySetBuilder", builderMethodName = "BySetBuilder")
    public Reply(String contents, Board board, User user) {
        this.adopt = ReplyAdopt.N;
        this.contents = contents;
        this.at = new Date();
        this.board = board;
        this.user = user;
        this.depth = 0;
    }

    @Builder(builderClassName="ReReplyBuilder", builderMethodName = "ReReplyBuilder")
    public Reply(String contents, Board board, User user, Long superSeq, Integer depth) {
        this.adopt = ReplyAdopt.N;
        this.contents = contents;
        this.at = new Date();
        this.board = board;
        this.user = user;
        this.superSeq = superSeq;
        this.depth = depth;
    }
}
