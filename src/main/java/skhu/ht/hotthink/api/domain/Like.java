package skhu.ht.hotthink.api.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import skhu.ht.hotthink.api.domain.enums.BoardType;

import javax.persistence.*;

@Getter
@Entity(name = "ELike")//DB 키워드와 중복으로 인해 변경.
@Table(name = "TB_LIKE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="LK_SEQ")
    private Long seq;
    @ManyToOne
    @JoinColumn(name="UR_SEQ")
    private User user;

    @Column(name="BOARD_SEQ")
    private Long bdSeq;

    @Enumerated(EnumType.STRING)
    @Column(name="BOARD_TYPE")
    private BoardType boardType;

    @Builder(builderClassName = "ByCreateBuilder", builderMethodName = "ByCreateBuilder")
    public Like(User user, Long bdSeq, BoardType boardType) {
        this.user = user;
        this.bdSeq = bdSeq;
        this.boardType = boardType;
    }
}
