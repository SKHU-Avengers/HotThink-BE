package skhu.ht.hotthink.api.user.service;

import skhu.ht.hotthink.api.domain.User;
import skhu.ht.hotthink.api.user.model.NewUserDTO;
import skhu.ht.hotthink.security.model.dto.UserAuthenticationModel;

import java.util.List;

public interface UserService {
    //User
   // public List<User> findAll();
   // public boolean setUser(NewUserDTO newUserDTO, int initPoint);
    //public boolean saveUser(User user);
    //public User findByEmail(String email);
    public UserAuthenticationModel findUserByEmailAndPw(String email, String pw);


}
