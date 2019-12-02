package skhu.ht.hotthink.api.user.model;

import lombok.Data;

@Data
public class UserFollowDTO {
    private TargetUserDTO celebrity;
    private TargetUserDTO follower;
}
