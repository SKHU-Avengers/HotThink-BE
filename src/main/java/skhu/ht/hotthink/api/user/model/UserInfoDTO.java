package skhu.ht.hotthink.api.user.model;

import lombok.*;
import skhu.ht.hotthink.api.domain.Board;
import skhu.ht.hotthink.api.domain.Scrap;

import java.util.List;

@ToString
@Setter
public final class UserInfoDTO extends UserModificationDTO{
    private Long seq;
    private Integer point;
    private Integer realTicket;
    private List<Scrap> scraps;
    private List<Board> boards;
}
