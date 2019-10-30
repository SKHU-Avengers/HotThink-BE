package skhu.ht.hotthink.api.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skhu.ht.hotthink.api.domain.*;
import skhu.ht.hotthink.api.domain.enums.UseAt;
import skhu.ht.hotthink.api.idea.repository.BoardRepository;
import skhu.ht.hotthink.api.user.exception.ScrapNotFoundException;
import skhu.ht.hotthink.api.user.exception.UserConflictException;
import skhu.ht.hotthink.api.user.model.*;
import skhu.ht.hotthink.api.user.repository.PreferenceRepository;
import skhu.ht.hotthink.api.user.repository.FollowRepository;
import skhu.ht.hotthink.api.user.repository.ScrapRepository;
import skhu.ht.hotthink.api.user.repository.UserRepository;
import skhu.ht.hotthink.error.ErrorCode;
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
    ScrapRepository scrapRepository;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    ModelMapper modelMapper;

    /*
        작성자: 홍민석, 김영곤
        작성일: 19-10-07
        내용: 회원가입 정보를 바탕으로 새로운 계정생성.
        작성일: 19-10-22
        내용: 중복확인 추가
*/
    @Transactional
    public boolean setUser(NewUserDTO newUserDTO, int initPoint) {
        User entity = userRepository.findUserByEmail(newUserDTO.getEmail());
        if(entity != null) throw new UserConflictException(ErrorCode.EMAIL_CONFLICT);
        entity = userRepository.findUserByNickName(newUserDTO.getNickName());
        if(entity != null) throw new UserConflictException(ErrorCode.NICKNAME_CONFLICT);
        User user = modelMapper.map(newUserDTO,User.class);
        user.setAuth(RoleName.ROLE_MEMBER);
        user.setPoint(initPoint);//초기 포인트 설정
        user.setRealTicket(0);
        user.setUseAt(UseAt.N);//사용유무 설정
        List<Preference> preferenceList = new ArrayList<Preference>();
        for(String prefer : newUserDTO.getPreferenceList()) preferenceList.add(new Preference(prefer));
        user.setPreferences(preferenceList);
        userRepository.save(user);

        return true;
    }

    /*
        작성자: 홍민석
        작성일: 19-10-23
        내용: 스크랩한 게시물 리스트 반환

     */
    public List<ScrapInfoDTO> getScrapList(String nickName){
        User user = userRepository.findUserByNickName(nickName);
        return scrapRepository.findAllByUser(user).stream()
                .map(s -> modelMapper.map(s, ScrapInfoDTO.class))
                .collect(Collectors.toList());
    }


    /*
        작성자: 홍민석
        작성일: 19-10-23
        내용: 특정 게시판에서 스크랩한 게시물 리스트 반환

     */
    public List<ScrapInfoDTO> getScrapList(String nickName, String boardType){
        User user = userRepository.findUserByNickName(nickName);
        return scrapRepository.findAllByUserAndBoardType(user,boardType).stream()
                .map(s -> modelMapper.map(s, ScrapInfoDTO.class))
                .collect(Collectors.toList());
    }

    /*
        작성자: 홍민석
        작성일: 19-10-23
        내용: 게시판 스크랩 작성.
        성공시 CREATED 반환
     */
    @Transactional
    public boolean setScrap(String nickName, ScrapInDTO scrapInDto){
        User user = userRepository.findUserByNickName(nickName);
        Board board = boardRepository.findBoardByBdSeq(scrapInDto.getBdSeq());
        Scrap scrap = new Scrap();
        scrap.setUser(user);
        scrap.setBoard(board);
        if(scrapRepository.save(scrap)!=null) throw new ScrapNotFoundException();
        return true;
    }

    /*
        작성자: 홍민석
        작성일: 19-10-23
        내용: 게시판 스크랩 삭제.
        성공시 Success 반환

     */
    public boolean deleteScrap(String nickName, Long bdSeq){
        Scrap scrap = scrapRepository.findScrapByUserAndBoard(
                userRepository.findUserByNickName(nickName)
                ,boardRepository.findBoardByBdSeq(bdSeq)
        );
        scrapRepository.delete(scrap);

        if(scrapRepository.existsById(Integer.parseInt(scrap.getSeq().toString()))){
            return false;
        }
        return true;
    }

    public List<User> findAll(){
        return userRepository.findAll();
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

    public UserAuthenticationModel findUserByEmailAndPw(String email, String pw){
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
