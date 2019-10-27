package skhu.ht.hotthink.api.user.service;

import skhu.ht.hotthink.api.MessageState;
import skhu.ht.hotthink.api.user.model.NewUserDTO;
import skhu.ht.hotthink.api.user.model.UserInfoDTO;
import skhu.ht.hotthink.api.user.model.UserModificationDTO;
import skhu.ht.hotthink.security.model.dto.UserAuthenticationModel;

public interface UserService {
    MessageState setUser(NewUserDTO newUserDTO, int initPoint);
    boolean saveUser(UserModificationDTO user);
    UserAuthenticationModel findUserAuthByEmailAndPw(String email, String pw);
    UserInfoDTO findUserInfo();
}
