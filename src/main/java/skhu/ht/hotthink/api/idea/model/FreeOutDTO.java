package skhu.ht.hotthink.api.idea.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import skhu.ht.hotthink.api.domain.BoardType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.List;

@Data
public class FreeOutDTO {
    private Long bdSeq;
    @Enumerated(EnumType.STRING)
    private BoardType boardType;
    private Long seq;
    private Integer hits;
    private Date createAt;
    private String image;
    private String title;
    private String contents;
    private String category;
    private Integer like;
    private UserOutDTO user;
    private List<ReplyOutDTO> replies;
}