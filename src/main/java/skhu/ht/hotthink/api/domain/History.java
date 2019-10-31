package skhu.ht.hotthink.api.domain;

import lombok.Data;
import org.aspectj.weaver.GeneratedReferenceTypeDelegate;
import org.springframework.data.convert.ThreeTenBackPortConverters;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "History")
@Table(name = "TB_HISTORY")
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
}
