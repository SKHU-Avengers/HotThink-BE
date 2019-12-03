package skhu.ht.hotthink.api.domain;

import lombok.*;
import skhu.ht.hotthink.api.domain.enums.BoardReferenceType;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "Attach")
@Table(name = "TB_ATTACH")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="AC_SEQ")
    private Long seq;
    private String path;

    @Column(name = "BOARD_SEQ")
    private Long boardSeq;

    @Enumerated(EnumType.STRING)
    @Column(name = "BOARD_REFERENCE_TYPE")
    private BoardReferenceType boardReferenceType;
}
