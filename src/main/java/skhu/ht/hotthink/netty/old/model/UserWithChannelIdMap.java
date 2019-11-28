package skhu.ht.hotthink.netty.old.model;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserWithChannelIdMap {
    private static ConcurrentMap<String, String> USER_CHANNEL_ID_MAP;

    public static ConcurrentMap<String, String> getInstance(){
        if (USER_CHANNEL_ID_MAP == null) USER_CHANNEL_ID_MAP = new ConcurrentHashMap<>();
        return USER_CHANNEL_ID_MAP;
    }

    public UserWithChannelIdMap(){

    }
}
