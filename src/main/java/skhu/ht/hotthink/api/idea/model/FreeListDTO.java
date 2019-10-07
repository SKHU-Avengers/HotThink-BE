package skhu.ht.hotthink.api.idea.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import skhu.ht.hotthink.api.domain.Category;
import skhu.ht.hotthink.api.domain.User;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
public class FreeListDTO {
    private Long seq;
    private int hits;
    private String title;
    private Date date;
    private String image;
    private UserOutDTO user;
    private String category;
}
