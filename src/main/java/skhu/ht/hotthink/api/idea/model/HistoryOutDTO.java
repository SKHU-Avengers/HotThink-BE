package skhu.ht.hotthink.api.idea.model;

import lombok.Data;

import java.util.Date;

@Data
public class HistoryOutDTO {
    private Date updateAt;

    private String title;
    private String image;
    private String contents;
}
