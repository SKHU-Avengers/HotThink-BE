package skhu.ht.hotthink.api.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skhu.ht.hotthink.api.domain.User;
import skhu.ht.hotthink.api.user.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }
    @Override
    public boolean saveUser(User user){
        userRepository.save(user);
        return true;
    }
    @Override
    public User findByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        return user;
    }
}
