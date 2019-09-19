package skhu.ht.hotthink.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;

@Data
@Entity
public class Pro {
    Date start;
    int membership;

    @OneToOne
    @JoinColumn(name = "UR_SEQ")
    User user;
}
