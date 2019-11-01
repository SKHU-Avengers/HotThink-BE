package skhu.ht.hotthink.api.domain;

import lombok.*;
import org.aspectj.weaver.GeneratedReferenceTypeDelegate;
import org.springframework.data.convert.ThreeTenBackPortConverters;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity(name = "History")
@Table(name = "TB_HISTORY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="HT_SEQ")
    private Long seq;
    @Column(name="UPDATE_AT")
    private Date updateAt;
    private String title;
    @Column(name="THUMBNAIL_IMG")
    private String image;
    private String contents;

    @ManyToOne
    @JoinColumn(name="BD_SEQ")
    private Board board;
    @Builder
    public History(String title, String image, String contents, Board board) {
        this.updateAt = new Date();
        this.title = title;
        this.image = image;
        this.contents = contents;
        this.board = board;
    }
}
