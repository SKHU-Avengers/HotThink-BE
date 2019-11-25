package skhu.ht.hotthink.api.domain.enums;

public enum RoleName {
    ROLE_NONE(0),
    ROLE_MEMBER(1),
    ROLE_SUBSCR(2),
    ROLE_ADMIN(3);
    private Integer grade;
    RoleName(int grade){
        this.grade = grade;
    }
    public int compare(RoleName arg){
        return this.grade-arg.grade;
    }
}
