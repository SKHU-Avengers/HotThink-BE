package skhu.ht.hotthink.api.idea.model;

import lombok.Data;

import java.util.Date;

@Data
public class ReplyInDTO {
    private Long frSeq;
    private Long seq;
    private String category;
    private Date at;
    private String contents;
    private String nickName;
}
