package skhu.ht.hotthink.api.domain;

import lombok.*;
import skhu.ht.hotthink.api.domain.enums.ReplyAdopt;

import javax.persistence.*;
import java.util.Date;

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
    @NonNull
    private Integer good;

    @ManyToOne
    @JoinColumn(name = "BD_SEQ")
    private Board board;

    @Setter
    @ManyToOne
    @JoinColumn(name = "SUB_RP_SEQ")
    private Reply reply;

    @ManyToOne
    @JoinColumn(name = "UR_SEQ")
    private User user;
    @Builder(builderClassName="BySetBuilder", builderMethodName = "BySetBuilder")
    public Reply(String contents, Board board, User user) {
        this.adopt = ReplyAdopt.N;
        this.contents = contents;
        this.at = new Date();
        this.good = 0;
        this.board = board;
        this.user = user;
    }
}
