package skhu.ht.hotthink.api.idea.model.reply;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class ReplyInDTO{
    private String contents;
    private String nickName;
    @JsonIgnore
    private Long bdSeq;
    @JsonIgnore
    private Long superRpSeq;
    public ReplyInDTO(){superRpSeq=null;}
}
