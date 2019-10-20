package skhu.ht.hotthink.api.idea.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class FreeOutDTO {
    private Long frSeq;
    private Long seq;
    private Integer hits;
    private Date createAt;
    private String image;
    private String title;
    private String contents;
    private String category;
    private Integer good;
    private UserOutDTO user;
    private List<ReplyOutDTO> replies;
}