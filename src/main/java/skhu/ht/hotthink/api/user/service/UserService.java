package skhu.ht.hotthink.api.user.service;

import skhu.ht.hotthink.api.domain.User;
import skhu.ht.hotthink.api.user.model.NewUserDTO;
import skhu.ht.hotthink.api.user.model.UserInfoDTO;
import skhu.ht.hotthink.api.user.model.UserModificationDTO;
import skhu.ht.hotthink.security.model.dto.UserAuthenticationModel;

import java.util.List;

public interface UserService {
    boolean setUser(NewUserDTO newUserDTO, int initPoint);
    boolean saveUser(UserModificationDTO user);
    UserAuthenticationModel findUserAuthByEmailAndPw(String email, String pw);
    UserInfoDTO findUserInfo();
}
