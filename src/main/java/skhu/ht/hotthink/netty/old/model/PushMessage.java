package skhu.ht.hotthink.netty.old.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
public class PushMessage {
    private String message;
    private String group;
    private String client;

    public PushMessage(String message, String group, String client) {
        this.message = message;
        this.group = group;
        this.client = client;
    }
}
