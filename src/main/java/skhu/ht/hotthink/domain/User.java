package skhu.ht.hotthink.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int seq;

    String email;
    String nickName;
    String name;
    String pw;
    String tel;
    String auth;
    int point;
    int realTicket;
}
