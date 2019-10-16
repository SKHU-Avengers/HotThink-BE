package skhu.ht.hotthink.model.domain;

import lombok.*;

import javax.persistence.*;

/*
* DB 유저테이블의 실제 값들을 맵핑하기 위한 엔티티클래스
* 용도:
* */

@ToString
@Entity
@Table(name = "tb_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of ={"email", "nickName"})
public class User {
    public static String 관리자 = "ROLE_SUPER";
    public static String 운영자 = "ROLE_MANAGE";
    public static String 일반회원 = "ROLE_USER";
    public static String 불량회원 = "ROLE_SUSPEND";
    public static String 구독자 = "ROLE_SUBSCR";
    public static String 비회원 = "ROLE_NONE";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int seq;

    @Getter
    String email;
    @Getter
    String nickName;
    String name;
    @Getter
    String pw;
    String tel;
    @Getter
    String auth;
    int point;
    int realTicket;
}
