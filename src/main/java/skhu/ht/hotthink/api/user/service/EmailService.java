package skhu.ht.hotthink.api.user.service;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import skhu.ht.hotthink.DateUtil;
import skhu.ht.hotthink.api.domain.EmailConfirm;
import skhu.ht.hotthink.api.domain.User;
import skhu.ht.hotthink.api.domain.enums.UseAt;
import skhu.ht.hotthink.api.user.exception.UserConflictException;
import skhu.ht.hotthink.api.user.model.NewUserDTO;
import skhu.ht.hotthink.api.user.repository.EmailConfirmRepository;
import skhu.ht.hotthink.api.user.repository.UserRepository;
import skhu.ht.hotthink.error.ErrorCode;

import java.util.Date;


@Slf4j
@Service
public class EmailService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailConfirmRepository emailConfirmRepository;
    @Setter
    JavaMailSender emailSender;
    private final Integer minRange = 100000;
    private final Integer maxRange = 999999999;
    private final int expiredPeriod = 7; //7일간 유효



    private void sendSimpleMessage(String to, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        try {
            emailSender.send(message);
        }catch(MailException e){
            log.info("메일 전송실패");
            e.printStackTrace();
        }
    }

    public boolean sendEmail(NewUserDTO newUserDTO){
        String subject = "[HotThink] 회원확인 이메일입니다.";
        String text ="아래의 URL을 클릭해 인증을 완료해주세요.\n";
        String url= ServletUriComponentsBuilder.fromCurrentContextPath()
                .toUriString();

        EmailConfirm emailConfirm = new EmailConfirm();
        emailConfirm.setUser(userRepository.findUserByEmail(newUserDTO.getNickName()));
        if(emailConfirm.getUser()!=null) throw new UserConflictException(ErrorCode.EMAIL_CONFLICT);
        emailConfirm.setEmailKey((long)(Math.random()*(maxRange-minRange+1))+minRange);
        emailConfirm.setRegisterAt(new Date());
        emailConfirm.setExpiredAt(DateUtil.addDate(new Date(),expiredPeriod));
        emailConfirmRepository.save(emailConfirm);
        text.concat(url.concat("/user/").concat(newUserDTO.getNickName())
                .concat("/key/").concat(emailConfirm.getEmailKey().toString()));
        sendSimpleMessage(newUserDTO.getEmail(),subject,text);
        return true;
    }

    public boolean confirmEmail(String nickName, Long emailKey) {
        User user = userRepository.findUserByNickName(nickName);
        EmailConfirm emailConfirm = emailConfirmRepository.findEmailConfirmByUser(user);
        if(DateUtil.isValid(emailConfirm.getExpiredAt()) && emailConfirm.getEmailKey().equals(emailKey)){
            user.setUseAt(UseAt.Y);//사용가능한 상태로 변경
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
