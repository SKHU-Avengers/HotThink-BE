package skhu.ht.hotthink.api.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skhu.ht.hotthink.api.domain.Follow;
import skhu.ht.hotthink.api.domain.Preference;
import skhu.ht.hotthink.api.domain.RoleName;
import skhu.ht.hotthink.api.domain.User;
import skhu.ht.hotthink.api.user.model.FollowDTO;
import skhu.ht.hotthink.api.user.model.NewUserDTO;
import skhu.ht.hotthink.api.user.model.UserModificationDTO;
import skhu.ht.hotthink.api.user.repository.PreferenceRepository;
import skhu.ht.hotthink.api.user.repository.FollowRepository;
import skhu.ht.hotthink.api.user.repository.UserRepository;
import skhu.ht.hotthink.security.model.dto.UserAuthenticationModel;

import java.util.ArrayList;
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
        작성자: 홍민석, 수정자: 김영곤
        작성일: 19-10-07, 수정일: 19-10-23
        내용: 회원가입 정보를 바탕으로 새로운 계정을 생성합니다.
    */
    @Override
    public int setUser(NewUserDTO newUserDTO) {
        if(userRepository.findUserByEmail(newUserDTO.getEmail())!=null) return 1;
        else if(userRepository.findUserByNickName(newUserDTO.getNickName())!=null) return 2;
        User user = modelMapper.map(newUserDTO,User.class);
        user.setAuth(RoleName.ROLE_MEMBER);
        user.setPoint(0);//초기 포인트 설정
        user.setRealTicket(0);
        List<Preference> preferenceList = new ArrayList<Preference>();
        for(String s : newUserDTO.getPreferences()) preferenceList.add(new Preference(s));
        user.setPreferences(preferenceList);
        userRepository.save(user);
        return 0;
    }


    @Override
    public List<User> findAll(){
        return userRepository.findAll();
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
        Follow follow = followRepository.findFollowByFollowerAndCelebrity(from, to);
        followRepository.delete(follow);
        if (followRepository.existsById(follow.getSeq())) {
            return false;
        }
        return true;
    }

    /*
       작성자: 김영곤
       작성일: 19-10-22
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

    /*
      작성자: 김영곤
      작성일: 19-10-23
      내용: 유저 수정 메소드
    */
    @Override
    public boolean saveUser(UserModificationDTO userModificationDTO){
        if(userRepository.findUserByNickName(userModificationDTO.getNickName())!=null) return false;
        User entity = userRepository.findUserByEmail(userModificationDTO.getEmail());
        entity.setNickName(userModificationDTO.getNickName());
        entity.setPw(userModificationDTO.getPw());
        entity.setTel(userModificationDTO.getTel());
        List<Preference> preferences = new ArrayList<Preference>();
        for(String preference:userModificationDTO.getPreferences()) preferences.add(new Preference(preference));
        entity.setPreferences(preferences);
        userRepository.save(entity);
        return true;
    }
}
