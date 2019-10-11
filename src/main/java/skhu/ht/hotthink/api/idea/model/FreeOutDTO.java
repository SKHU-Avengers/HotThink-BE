package skhu.ht.hotthink.api.idea.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class FreeOutDTO {
    private Long frSeq;
    private String category;
    private Long seq;
    private Integer hits;
    private String title;
    private Date createAt;
    private String contents;
    private Integer good;
    private String image;
    private UserOutDTO user;
    private List<ReplyOutDTO> replies;
}