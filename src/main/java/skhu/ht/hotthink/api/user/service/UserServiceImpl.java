package skhu.ht.hotthink.api.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skhu.ht.hotthink.api.domain.Follow;
import skhu.ht.hotthink.api.domain.Preference;
import skhu.ht.hotthink.api.domain.RoleName;
import skhu.ht.hotthink.api.domain.User;
import skhu.ht.hotthink.api.user.model.FollowDTO;
import skhu.ht.hotthink.api.user.model.NewUserDTO;
import skhu.ht.hotthink.api.user.repository.PreferenceRepository;
import skhu.ht.hotthink.api.user.repository.FollowRepository;
import skhu.ht.hotthink.api.user.repository.UserRepository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

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
    @Transactional
    public Boolean checkOverlap(String email) {
        User user = userRepository.findUserByEmail(email);
        if(user == null){
            return false;
        }
        return true;
    }
    @Transactional
    public List<FollowDTO> getFollowerList(String nickName) {
        User celebrity = userRepository.findUserByNickName(nickName);
        return followRepository.findAllByCelebrity(celebrity)
                .stream().map(s -> modelMapper.map(s, FollowDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<FollowDTO> getFollowList(String nickName) {
        User follower = userRepository.findUserByNickName(nickName);
        return followRepository.findAllByFollower(follower)
                .stream().map(s -> modelMapper.map(s, FollowDTO.class))
                .collect(Collectors.toList());

    }
    @Transactional
    public boolean setFollow(String follower,String celebrity) {
        Follow follow = new Follow();
        follow.setFollower(userRepository.findUserByNickName(follower));
        follow.setCelebrity(userRepository.findUserByNickName(celebrity));
        if (followRepository.save(follow) == null){
            return false;
        }
        return true;
    }

    @Transactional
    public boolean deleteFollow(String follower,String celebrity) {
        User from = userRepository.findUserByNickName(follower);
        User to = userRepository.findUserByNickName(celebrity);
        //TODO: 권한인증 코드 작성
        Follow follow=followRepository.findFollowByFollowerAndCelebrity(from, to);
        followRepository.delete(follow);
        if (followRepository.existsById(follow.getSeq())){
            return false;
        }
        return true;
    }
}
