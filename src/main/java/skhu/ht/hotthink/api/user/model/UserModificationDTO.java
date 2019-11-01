package skhu.ht.hotthink.api.user.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import skhu.ht.hotthink.api.domain.Preference;

import java.util.List;

@Getter @Setter
@ToString
public class UserModificationDTO extends UserBase {
    private String pw;
    private String tel;
    private List<Preference> preferenceList;

    UserModificationDTO(){
        super();
    }
}
