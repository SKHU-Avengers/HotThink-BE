package skhu.ht.hotthink.api.user.service;

import skhu.ht.hotthink.api.domain.User;
import skhu.ht.hotthink.api.user.model.NewUserDTO;

import java.util.List;

public interface UserService {

    public List<User> findAll();
    public boolean setUser(NewUserDTO newUserDTO, int initPoint);
    public boolean saveUser(User user);
    public User findByEmail(String email);

}
