package skhu.ht.hotthink.api.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import skhu.ht.hotthink.api.domain.Preference;
import skhu.ht.hotthink.api.domain.RoleName;
import skhu.ht.hotthink.api.domain.User;
import skhu.ht.hotthink.api.user.model.NewUserDTO;
import skhu.ht.hotthink.api.user.repository.PreferenceRepository;
import skhu.ht.hotthink.api.user.repository.FollowRepository;
import skhu.ht.hotthink.api.user.repository.UserRepository;
import skhu.ht.hotthink.security.model.dto.UserAuthenticationModel;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    PreferenceRepository preferenceRepository;
    @Autowired
    FollowRepository followRepository;
    @Autowired
    ModelMapper modelMapper;

    /*
        작성자: 홍민석
        작성일: 19-10-07
        내용: 회원가입 정보를 바탕으로 새로운 계정을 생성합니다.
    */
    @Override
    public boolean setUser(NewUserDTO newUserDTO, int initPoint) {
        User user = modelMapper.map(newUserDTO,User.class);
        user.setAuth(RoleName.ROLE_MEMBER);
        user.setPoint(initPoint);//초기 포인트 설정
        user.setRealTicket(0);
        userRepository.save(user);
        Preference preference = new Preference();
        preference.setUser(user);
        for(String s : newUserDTO.getPreferences()){
            preference.setPreference(s);
            preferenceRepository.save(preference);
        }
        return true;
    }


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
        return null;
    }

    /*
        작성자: 홍민석
        작성일: 19-10-07
        내용: 아이디가 중복되었는지 검사합니다.
    */
    public Boolean checkOverlap(String email) {
        User user = userRepository.findUserByEmail(email);
        if(user == null){
            return false;
        }
        return true;
    }

    /*
       작성자: 김영곤
       작성일: 19-10-19
       내용: 아이디와 비밀번호로 유저를 찾고 유저권한모델로 맵핑
   */

    public UserAuthenticationModel findUserByEmailAndPw(String email, String pw){
        User entity = userRepository.findUserByEmail(email);
        if(entity == null) throw new UsernameNotFoundException("");
        else if(!entity.getPw().equals(pw)) throw new BadCredentialsException("");
        return UserAuthenticationModel.builder()
                .email(entity.getEmail())
                .pw(entity.getPw())
                .auth(new SimpleGrantedAuthority(entity.getAuth().toString())).build();
    }
}
