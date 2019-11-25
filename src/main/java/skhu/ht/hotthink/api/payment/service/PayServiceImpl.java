package skhu.ht.hotthink.api.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skhu.ht.hotthink.DateUtil;
import skhu.ht.hotthink.api.domain.*;
import skhu.ht.hotthink.api.domain.enums.IdeaState;
import skhu.ht.hotthink.api.domain.enums.PayCategory;
import skhu.ht.hotthink.api.domain.enums.RoleName;
import skhu.ht.hotthink.api.idea.exception.IdeaNotFoundException;
import skhu.ht.hotthink.api.idea.exception.UserUnauthorizedException;
import skhu.ht.hotthink.api.idea.repository.BoardRepository;
import skhu.ht.hotthink.api.idea.repository.RealRepository;
import skhu.ht.hotthink.api.payment.exception.MoneyException;
import skhu.ht.hotthink.api.payment.exception.PayInvalidException;
import skhu.ht.hotthink.api.payment.model.dto.PayInfoDTO;
import skhu.ht.hotthink.api.payment.model.dto.ReputationDTO;
import skhu.ht.hotthink.api.payment.repository.PayListRepository;
import skhu.ht.hotthink.api.payment.repository.ReputationRepository;
import skhu.ht.hotthink.api.payment.repository.SubscribeRepository;
import skhu.ht.hotthink.api.user.model.UserBase;
import skhu.ht.hotthink.api.user.repository.UserRepository;

import java.util.Date;

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
    public boolean setOne(PayInfoDTO payInfoDTO){
        User user = userRepository.findUserByEmail(findEmailBySpringSecurity());
        int price = initPrice;

        switch(payInfoDTO.getPayCategory()){
            case SUBSCRIBE:
                price = payInfoDTO.getPeriod().getPrice(payInfoDTO.getPrice());
                Subscribe subscribe = subscribeRepository.findSubscribeByUserEmail(findEmailBySpringSecurity());
                if(subscribe == null || !DateUtil.isValid(subscribe.getEnd())){//생성된 구독정보가 없으면 생성 후 기간 증가
                    Date start = new Date();
                    Date end = DateUtil.addDate(start, payInfoDTO.getPeriod().getPeriod());
                    user.setAuth(RoleName.ROLE_SUBSCR);
                    subscribe = Subscribe.builder()
                            .user(user)
                            .start(start)
                            .end(end)
                            .build();
                }else if(DateUtil.isValid(subscribe.getEnd())){ //구독만료가 안되었으면 연장
                    Date end = subscribe.getEnd();
                    subscribe.setEnd(DateUtil.addDate(end, payInfoDTO.getPeriod().getPeriod()));
                }
                subscribeRepository.save(subscribe);
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
                break;
            default:
                throw new PayInvalidException();
        }
        if(user.getPoint() < price) throw new MoneyException();

        userRepository.purchase(user.getEmail(), price);
        PayList payList = PayList.builder()
                .price(price)
                .payCategory(payInfoDTO.getPayCategory())
                .payMethod(payInfoDTO.getPayMethod())
                .user(user)
                .build();
        payListRepository.save(payList);
        if(payInfoDTO.getPayCategory()== PayCategory.REALTHINK) {
            Board board = boardRepository.findBoardByBdSeq(payInfoDTO.getBdSeq());
            Reputation reputation = Reputation.BasicBuilder()
                    .payList(payList)
                    .board(board)
                    .build();
            reputationRepository.save(reputation);
        }
        return true;
    }

    /*
        작성자: 홍민석
        작성일: 2019-11-25
        내용: 판매자 및 구매자가 상대방의 점수 및 평가 작성
    */
    @Transactional
    public boolean setReputation(ReputationDTO reputationDTO){
        Board board = boardRepository.findBoardByBdSeq(reputationDTO.getBdSeq());
        if(board == null) throw new IdeaNotFoundException();
        Reputation reputation = reputationRepository.findReputationByBoard(board);
        if(reputation == null) ;// TODO: 예외처리
        // throw new
        switch(reputationDTO.getReputationType()){
            case Buyer:
                if(!reputation.getBoard().getUser().getEmail().equals(findEmailBySpringSecurity())){
                    throw new PayInvalidException("판매자가 아닙니다.");
                }
                reputation.setBuyer(reputationDTO.getScore(), reputationDTO.getComments());
                break;
            case Seller:
                if(!reputation.getPayList().getUser().getEmail().equals(findEmailBySpringSecurity())){
                    throw new PayInvalidException("구매자가 아닙니다.");
                }
                reputation.setSeller(reputationDTO.getScore(), reputationDTO.getComments());
                break;
        }
        reputationRepository.save(reputation);
        return true;
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
