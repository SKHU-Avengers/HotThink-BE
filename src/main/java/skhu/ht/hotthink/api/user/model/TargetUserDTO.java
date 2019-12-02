package skhu.ht.hotthink.api.user.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Setter
@Getter
public class TargetUserDTO extends UserBase {
    private String profileImg;
    private List<UserInfoBoardModel> scraps;
    private List<UserInfoBoardModel> boards;
    private List<String> preferenceList;
    private List<UserBase> followers;
    private List<UserBase> followings;
}
