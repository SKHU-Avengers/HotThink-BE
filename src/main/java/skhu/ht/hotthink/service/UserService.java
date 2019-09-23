package skhu.ht.hotthink.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skhu.ht.hotthink.Repository.UserRepository;
import skhu.ht.hotthink.domain.User;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }
}
