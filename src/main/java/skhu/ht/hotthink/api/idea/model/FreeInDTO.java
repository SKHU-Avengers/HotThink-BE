package skhu.ht.hotthink.api.idea.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import skhu.ht.hotthink.api.domain.Category;
import skhu.ht.hotthink.api.domain.User;

import java.util.Date;

@Data
public class FreeInDTO {
    private String title;
    private Date date;
    private String contents;
    private int good;
    private String image;
}
