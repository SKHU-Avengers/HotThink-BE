package skhu.ht.hotthink.api.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skhu.ht.hotthink.api.domain.PayList;
import skhu.ht.hotthink.api.domain.Real;
import skhu.ht.hotthink.api.domain.Reputation;
import skhu.ht.hotthink.api.domain.User;
import skhu.ht.hotthink.api.domain.enums.IdeaState;
import skhu.ht.hotthink.api.idea.exception.IdeaNotFoundException;
import skhu.ht.hotthink.api.idea.exception.UserUnauthorizedException;
import skhu.ht.hotthink.api.idea.repository.BoardRepository;
import skhu.ht.hotthink.api.idea.repository.RealRepository;
import skhu.ht.hotthink.api.payment.model.dto.PayInfoDTO;
import skhu.ht.hotthink.api.payment.repository.PayListRepository;
import skhu.ht.hotthink.api.payment.repository.ReputationRepository;
import skhu.ht.hotthink.api.payment.repository.SubscribeRepository;
import skhu.ht.hotthink.api.user.model.UserBase;
import skhu.ht.hotthink.api.user.repository.UserRepository;

@Service
public class PayServiceImpl {
    @Autowired
    ReputationRepository reputationRepository;
    @Autowired
    PayListRepository payListRepository;
    @Autowired
    SubscribeRepository subscribeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    RealRepository realRepository;

    final static int initPrice=0;


    /*
        작성자: 홍민석
        작성일: 2019-11-19
        내용: 구독, 프리패스, 리얼띵크 구매 기능.
     */
    @Transactional
    public void setOne(PayInfoDTO payInfoDTO){
        int price = initPrice;

        switch(payInfoDTO.getPayCategory()){
            case SUBSCRIBE:
                price = payInfoDTO.getPeriod().getPrice(payInfoDTO.getPrice());
                //TODO : DB 기간증가 코드 작성
                break;
            case FREEPASS:
                price = payInfoDTO.getPrice();
                userRepository.increaseRealTicket(findEmailBySpringSecurity());
                break;
            case REALTHINK:
                price = payInfoDTO.getPrice();
                Real real = boardRepository.findBoardByBdSeq(payInfoDTO.getBdSeq()).getReals().get(0);
                if(real==null)throw new IdeaNotFoundException();
                real.setState(IdeaState.FINISH);
                real.setRightHolder(findEmailBySpringSecurity());
                realRepository.save(real);
                //TODO : 구매자 후기 작성
                break;
            default:
                //TODO : 예외처리
        }
        User user = userRepository.findUserByEmail(findEmailBySpringSecurity());
        if(user.getPoint() < price){ }//TODO:금액부족 예외처리

        userRepository.purchase(user.getEmail(), price);
        PayList payList = PayList.builder()
                .price(price)
                .payCategory(payInfoDTO.getPayCategory())
                .payMethod(payInfoDTO.getPayMethod())
                .user(user)
                .build();
        payListRepository.save(payList);
    }

    /*
        작성자: 홍민석
        작성일: 2019-11-03
        내용: 권한 인증
        로그인 안되어 있을시 예외처리
     */
    private static String findEmailBySpringSecurity(){
        String email = (String)((UserBase) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail().toString();
        if(email == null){ throw new UserUnauthorizedException("권한없는 사용자"); }
        return email;
    }

}
