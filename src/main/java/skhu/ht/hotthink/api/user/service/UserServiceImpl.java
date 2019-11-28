package skhu.ht.hotthink.api.user.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skhu.ht.hotthink.DateUtil;
import skhu.ht.hotthink.api.domain.*;
import skhu.ht.hotthink.api.domain.enums.RoleName;
import skhu.ht.hotthink.api.domain.enums.UseAt;
import skhu.ht.hotthink.api.idea.repository.BoardRepository;
import skhu.ht.hotthink.api.user.model.*;
import skhu.ht.hotthink.api.user.exception.ScrapNotFoundException;
import skhu.ht.hotthink.api.user.exception.UserConflictException;
import skhu.ht.hotthink.api.user.model.*;
import skhu.ht.hotthink.api.user.repository.PreferenceRepository;
import skhu.ht.hotthink.api.user.repository.FollowRepository;
import skhu.ht.hotthink.api.user.repository.ScrapRepository;
import skhu.ht.hotthink.api.user.repository.UserRepository;
import skhu.ht.hotthink.security.exceptions.PasswordErrorException;
import skhu.ht.hotthink.error.ErrorCode;
import skhu.ht.hotthink.security.model.dto.UserAuthenticationModel;

import java.lang.reflect.Type;
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
        작성일: 19-10-23
        내용: 반환형 MessageState로 수정.
        작성일: 19-10-27
        내용: Preference 리스트 맵핑
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
        user.setUseAt(UseAt.Y);//사용유무 설정
        setPreference(user, user.getPreferenceList());
        userRepository.save(user);

        return true;
    }
    /*
        작성자: 홍민석
        작성일: 19-10-23
        내용: 특정 게시판에서 스크랩한 게시물 리스트 반환

     */
    @Transactional
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
    public boolean setScrap(String nickName, Long boardId){
        User user = userRepository.findUserByNickName(nickName);
        Board board = boardRepository.findBoardByBdSeq(boardId);
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
        return followRepository.save(follow) == null?false:true;
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
        if(entity == null) throw new UsernameNotFoundException("MessageState");
        else if(!entity.getPw().equals(pw)) throw new PasswordErrorException("Password_Error");
        return UserAuthenticationModel.builder()
                .email(entity.getEmail())
                .pw(entity.getPw())
                .nickName(entity.getNickName())
                .auth(new SimpleGrantedAuthority(entity.getAuth().toString())).build();
    }

    /*
       작성자: 김영곤
       작성일: 19-10-24
       내용: 이메일로 유저 조회하여 마이페이지 모델로 맵핑
    */
    @Override
    public UserInfoDTO findUserInfo() {
        //유저 기본 정보
        User entity = userRepository.findUserByEmail(getUserEmailFromSecurity());
        UserInfoDTO user = modelMapper.map(entity, UserInfoDTO.class);
        //내가 쓴글
        Type listType = new TypeToken<List<UserInfoBoardModel>>() {}.getType();
        List<Board> boards = boardRepository.findAllByUser(entity);
        List<UserInfoBoardModel> myBoards = modelMapper.map(boards, listType);
        user.setBoards(myBoards);
        //스크랩
        boards.clear();
        for(Scrap scrap : scrapRepository.findAllByUser(entity)) boards.add(scrap.getBoard());
        List<UserInfoBoardModel> scrapBoards = modelMapper.map(boards, listType);
        user.setScraps(scrapBoards);
        user.setSeq((long) -892);
        if(entity.getSubscribe()!=null && DateUtil.isValid(entity.getSubscribe().getEnd())) {
            user.setSubscribe(modelMapper.map(entity.getSubscribe(), SubscribeInfoDTO.class));
        }
        return user;
    }

    /*
      작성자: 김영곤
      작성일: 19-10-24
      내용: 유저 수정 메소드
      작성일: 19-10-27
      내용: Preference 리스트 수정 완료
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
        setPreference(entity, userModificationDTO.getPreferenceList());
        entity.getPreferenceList().removeAll(comparePreferenceList(entity.getPreferenceList(), userModificationDTO.getPreferenceList()));
        entity.getPreferenceList().addAll(comparePreferenceList(userModificationDTO.getPreferenceList(), entity.getPreferenceList()));
        return true;
    }

    /*
      작성자: 김영곤
      작성일: 19-10-27
      내용: Preference 리스트들 유저 셋팅
    */
    private static void setPreference(User user, List<Preference> preferenceList){
        for(Preference preference:preferenceList) preference.setUser(user);
    }

    /*
      작성자: 김영곤
      작성일: 19-10-27
      내용: Preference 리스트 비교 후 없는 리스트 반환
    */
    private List<Preference> comparePreferenceList(List<Preference> source, List<Preference> destination){
        List<Preference> preferenceList = new ArrayList<>();
        for(Preference preference:source)
            if (!destination.contains(preference)) preferenceList.add(preference);
        return preferenceList;
    }

    /*
      작성자: 김영곤
      작성일: 19-10-24
      내용: 토큰 검증 후 Context에 저장된 유저의 이메일을 받아옴
    */
    private static String getUserEmailFromSecurity(){
        return ((UserBase) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
    }
}
