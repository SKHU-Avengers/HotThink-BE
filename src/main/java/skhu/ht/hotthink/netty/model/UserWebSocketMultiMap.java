package skhu.ht.hotthink.netty.model;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import io.netty.channel.ChannelHandlerContext;

public class UserWebSocketMultiMap {
    private static ConcurrentMap<String, ConcurrentMap<String, ChannelHandlerContext>> USER_CHANNEL_MAP;

    public static ConcurrentMap<String, ConcurrentMap<String, ChannelHandlerContext>> getInstance() {
        if(USER_CHANNEL_MAP == null) {
            USER_CHANNEL_MAP = new ConcurrentHashMap<>();
        }

        return USER_CHANNEL_MAP;
    }

    private UserWebSocketMultiMap() {}
}
