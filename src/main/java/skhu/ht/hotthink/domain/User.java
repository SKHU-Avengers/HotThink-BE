package skhu.ht.hotthink.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int seq;

    String name;
    String nickName;
    String email;
    String pw;
    String tel;
    String auth;
    int point;
}
