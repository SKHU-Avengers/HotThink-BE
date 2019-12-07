package skhu.ht.hotthink.api.domain;

import io.jsonwebtoken.lang.Assert;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity(name = "Reputation")
@Table(name = "TB_REPUTATION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reputation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paySeq;
    @Column(name = "BUYER_SCORE")
    private Integer buyerScore;
    @Column(name = "SELLER_SCORE")
    private Integer sellerScore;
    @Column(name = "BUYER_COMMENTS")
    private String buyerComments;
    @Column(name = "SELLER_COMMENTS")
    private String sellerComments;

    @MapsId
    @OneToOne
    @JoinColumn(name = "IP_SEQ")
    private PayList payList;

    @MapsId
    @OneToOne
    @JoinColumn(name = "BD_SEQ")
    private Board board;

    @Builder(builderClassName = "BasicBuilder",builderMethodName = "BasicBuilder")
    public Reputation(PayList payList, Board board) {
        this.payList = payList;
        this.board = board;
    }

    @Builder(builderClassName = "BuyerBuilder",builderMethodName = "BuyerBuilder")
    public Reputation(Integer sellerScore, String sellerComments, PayList payList, Board board) {
        setSeller(sellerScore,sellerComments);
        this.payList = payList;
        this.board = board;
    }

    @Builder(builderClassName = "SellerBuilder",builderMethodName = "SellerBuilder")
    public Reputation(PayList payList, Integer buyerScore, String buyerComments, Board board) {
        setBuyer(buyerScore,buyerComments);
        this.payList = payList;
        this.board = board;
    }


    public void setBuyer(@NonNull Integer buyerScore, @NonNull String buyerComments){
        Assert.notNull(buyerScore,"구매자 평가가 있어야합니다.");
        Assert.notNull(buyerComments,"구매자 후기가 있어야합니다.");
        this.buyerScore = buyerScore;
        this.buyerComments = buyerComments;
    }

    public void setSeller(@NonNull Integer sellerScore, @NonNull String sellerComments){
        Assert.notNull(sellerScore,"구매자 평가가 있어야합니다.");
        Assert.notNull(sellerComments,"구매자 후기가 있어야합니다.");
        this.sellerScore = sellerScore;
        this.sellerComments = sellerComments;
    }
}
