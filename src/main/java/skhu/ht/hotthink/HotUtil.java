package skhu.ht.hotthink;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import skhu.ht.hotthink.api.MessageState;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class HotUtil {
    Map<MessageState, HttpStatus> map;

    public HotUtil(){
        map=new HashMap<>();
        map.put(MessageState.Success,HttpStatus.OK);
        map.put(MessageState.Fail,HttpStatus.BAD_REQUEST);
        map.put(MessageState.NotExist,HttpStatus.OK);
        map.put(MessageState.Conflict,HttpStatus.CONFLICT);
        map.put(MessageState.Error,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
