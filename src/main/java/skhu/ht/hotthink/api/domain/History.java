package skhu.ht.hotthink.api.domain;

import org.aspectj.weaver.GeneratedReferenceTypeDelegate;
import org.springframework.data.convert.ThreeTenBackPortConverters;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "History")
@Table(name = "TB_HISTORY")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    private Date updateAt;

    private String title;
    private String image;
    private String contents;

    @ManyToOne
    @JoinColumn(name="BD_SEQ")
    private Board board;
}
