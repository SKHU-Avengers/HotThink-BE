package skhu.ht.hotthink.api.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skhu.ht.hotthink.api.MessageState;
import skhu.ht.hotthink.api.domain.*;
import skhu.ht.hotthink.api.user.model.*;
import skhu.ht.hotthink.api.user.repository.PreferenceRepository;
import skhu.ht.hotthink.api.user.repository.FollowRepository;
import skhu.ht.hotthink.api.user.repository.UserRepository;
import skhu.ht.hotthink.security.model.dto.UserAuthenticationModel;

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
        작성자: 홍민석, 김영곤
        작성일: 19-10-07
        내용: 회원가입 정보를 바탕으로 새로운 계정생성.
        작성일: 19-10-22
        내용: 중복확인 추가
        작성일: 19-10-23
        내용: 반환형 MessageState로 수정.
        작성일: 19-10-23
        내용: 유저모델 수정
    */
    public MessageState setUser(NewUserDTO newUserDTO, int initPoint) {
        //닉네임 중복 추가
        if(userRepository.findUserByEmail(newUserDTO.getEmail())!=null||
                userRepository.findUserByNickName(newUserDTO.getNickName())!=null) return MessageState.Conflict;
        User user = modelMapper.map(newUserDTO,User.class);
        user.setAuth(RoleName.ROLE_MEMBER);
        user.setPoint(initPoint);//초기 포인트 설정
        user.setRealTicket(0);
        user.setUseAt(UseAt.Y);//사용유무 설정
        userRepository.save(user);
        for(String str:newUserDTO.getPreferences()) {
            Preference preference = new Preference(str, user);
            preferenceRepository.save(preference);
        }
        return MessageState.Created;
    }


    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
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

    /*
        작성자: 홍민석
        작성일: 19-10-23
        내용: nickname을 팔로우하는 사람 목록 불러오기.
     */

    @Transactional
    public List<FollowDTO> getFollowerList(String nickName) {
        User celebrity = userRepository.findUserByNickName(nickName);
        return followRepository.findAllByCelebrity(celebrity)
                .stream().map(s -> modelMapper.map(s, FollowDTO.class))
                .collect(Collectors.toList());
    }

    /*
        작성자: 홍민석
        작성일: 19-10-23
        내용: nickname이 팔로우하는 사람 목록 불러오기.
     */
    @Transactional
    public List<FollowDTO> getFollowList(String nickName) {
        User follower = userRepository.findUserByNickName(nickName);
        return followRepository.findAllByFollower(follower)
                .stream().map(s -> modelMapper.map(s, FollowDTO.class))
                .collect(Collectors.toList());

    }
    /*
        작성자: 홍민석
        작성일: 19-10-23
        내용: 팔로우 시작.
     */
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
    /*
        작성자: 홍민석
        작성일: 19-10-23
        내용: 팔로우 취소.
     */
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
       작성일: 19-10-19
       내용: 아이디와 비밀번호로 유저를 찾고 유저권한모델로 맵핑
   */
    public UserAuthenticationModel findUserAuthByEmailAndPw(String email, String pw){
        User entity = userRepository.findUserByEmail(email);
        if(entity == null) throw new UsernameNotFoundException("");
        else if(!entity.getPw().equals(pw)) throw new BadCredentialsException("");
        return UserAuthenticationModel.builder()
                .email(entity.getEmail())
                .pw(entity.getPw())
                .nickName(entity.getNickName())
                .auth(new SimpleGrantedAuthority(entity.getAuth().toString())).build();
    }

    /*
       작성자: 김영곤
       작성일: 19-10-23
       내용: 이메일로 유저 조회하여 마이페이지 모델로 맵핑
    */
    @Override
    public UserInfoDTO findUserInfoByEmail(String email) {
        User entity = userRepository.findUserByEmail(email);
        UserInfoDTO user = modelMapper.map(userRepository.findUserByEmail(email), UserInfoDTO.class);
        return user;
    }

    /*
      작성자: 김영곤
      작성일: 19-10-24
      내용: 유저 수정 메소드
    */
    @Override
    @Transactional
    public boolean saveUser(UserModificationDTO userModificationDTO){
        //유저 수정
        User entity = userRepository.findUserByEmail(getUserEmailFromSecurity());
        if(!entity.getNickName().equals(userModificationDTO.getNickName())&&//유저 닉네임 바뀌었는지
                userRepository.findUserByNickName(userModificationDTO.getNickName())!=null) return false;
        entity.setPw(userModificationDTO.getPw());
        entity.setTel(userModificationDTO.getTel());
        entity.setNickName(userModificationDTO.getNickName());
        userRepository.save(entity);
        //Preference 수정
        List<Preference> preferences = preferenceRepository.findAllByUser(entity);
        for(Preference preference:preferences)
            if(!userModificationDTO.getPreferences().contains(preference.getPreference())) preferenceRepository.delete(preference);//없어진것 삭제
        for(String preference:userModificationDTO.getPreferences())
            if(!preferences.contains(new Preference(preference,null))) preferenceRepository.save(new Preference(preference, entity));//새로 추가
        return true;
    }


    /*
      작성자: 김영곤
      작성일: 19-10-24
      내용: 토큰 검증 후 Context에 저장된 유저의 이메일을 받아옴
    */
    private static String getUserEmailFromSecurity(){
        return ((UserBase)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
    }
}
