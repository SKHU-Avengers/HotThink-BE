package skhu.ht.hotthink.api.domain;

import io.jsonwebtoken.lang.Assert;
import lombok.*;
import skhu.ht.hotthink.api.domain.enums.PayCategory;
import skhu.ht.hotthink.api.domain.enums.PayMethod;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity(name = "PayList")
@Table(name = "TB_PAY_LIST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IP_SEQ")
    private Long seq;
    @NonNull
    private Date payAt;
    @NonNull
    private PayMethod payMethod;
    @NonNull
    private PayCategory payCategory;
    @NonNull
    private Integer price;
    @ManyToOne
    @JoinColumn(name="UR_BUYER")
    private User user;

    @Builder
    public PayList(@NonNull Integer price, @NonNull PayMethod payMethod, @NonNull PayCategory payCategory, @NonNull User user) {
        Assert.notNull(user,"구독자 정보가 누락되었습니다.");
        Assert.notNull(payMethod,"지불 정보가 누락되었습니다.");
        Assert.notNull(payCategory,"지불 종류가 누락되었습니다.");
        Assert.notNull(price,"지불 금액이 누락되었습니다.");
        this.payMethod = payMethod;
        this.user = user;
        this.payAt = new Date();
        this.payCategory = payCategory;
        this.price = price;
    }
}
