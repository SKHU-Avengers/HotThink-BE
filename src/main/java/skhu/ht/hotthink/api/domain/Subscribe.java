package skhu.ht.hotthink.api.domain;


import io.jsonwebtoken.lang.Assert;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity(name = "Subscribe")
@Table(name = "TB_SUBSCRIBE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UR_SEQ")
    private Long seq;
    @NonNull
    @Column(name = "S_START")
    private Date start;
    @NonNull
    @Column(name = "S_END")
    private Date end;

    @MapsId
    @OneToOne
    @JoinColumn(name="UR_SEQ")
    private User user;

    @Builder
    public Subscribe(@NonNull Date start, @NonNull Date end, User user) {
        Assert.notNull(start,"구독 시작 날짜가 비었습니다.");
        Assert.notNull(end,"구독 종료 날짜가 비었습니다.");
        Assert.notNull(user,"구독자가 비었습니다.");

        this.start = start;
        this.end = end;
        this.user = user;
    }
}
