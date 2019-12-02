package skhu.ht.hotthink.api.domain;

import lombok.Data;
import skhu.ht.hotthink.api.domain.enums.BoardReferenceType;

import javax.persistence.*;

@Data
@Entity(name = "Attach")
@Table(name = "TB_ATTACH")
public class Attach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="AC_SEQ")
    private Long seq;
    private String path;
    @Column(name = "BOARD_SEQ")
    private Long boardSeq;
    @Column(name = "BOARD_REFERENCE_TYPE")
    private BoardReferenceType boardReferenceType;
}
