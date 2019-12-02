package skhu.ht.hotthink.api.user.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class SubscribeInfoDTO {
    private Date start;
    private Date end;
}
