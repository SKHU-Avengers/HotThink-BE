package skhu.ht.hotthink.api.user.model;

import lombok.*;
import java.util.List;

@ToString
@Setter @Getter
public final class UserInfoDTO extends UserModificationDTO{
    private Long seq;
    private String name;
    private Integer point;
    private Integer realTicket;
    private List<UserInfoBoardModel> scraps;
    private List<UserInfoBoardModel> boards;

    public UserInfoDTO(){
        super();
    }
}
