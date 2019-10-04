package skhu.ht.hotthink.api.user.service;

import skhu.ht.hotthink.api.domain.User;

import java.util.List;

public interface UserService {

    public List<User> findAll();
    public boolean saveUser(User user);
    public User findByEmail(String email);

}
