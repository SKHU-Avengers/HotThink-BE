package skhu.ht.hotthink.api.user.model;

import lombok.Data;
import skhu.ht.hotthink.api.idea.model.UserOutDTO;

@Data
public class FollowDTO {
    private UserOutDTO celebrity;
    private UserOutDTO follower;
}
