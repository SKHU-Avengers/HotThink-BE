package skhu.ht.hotthink.api.user.model;

import lombok.Data;
import skhu.ht.hotthink.api.idea.model.UserOutDTO;

@Data
public class ScrapInfoDTO {
    private String title;
    private String image;
    private UserOutDTO userOutDTO;
}