package skhu.ht.hotthink.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skhu.ht.hotthink.netty.model.UserWebSocketMultiMap;
import skhu.ht.hotthink.netty.model.UserWithChannelIdMap;

import java.nio.charset.Charset;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
@Slf4j
public class WebSocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame sockFrame) throws Exception {
        String initUserId = ((ByteBuf) sockFrame.content()).toString(Charset.defaultCharset());
        Logger log= LoggerFactory.getLogger(this.getClass());
        log.debug("등록 중");
        if(this.registryUserChannel(initUserId,ctx)){
            System.out.println("registry");
        }
        System.out.println(initUserId.concat(" : ").concat(ctx.toString()));
    }

    private boolean registryUserChannel(String userId, ChannelHandlerContext chc) {
        String channelId = chc.channel().id().toString();

        ConcurrentMap<String,String> channelIdMap = UserWithChannelIdMap.getInstance();
        ConcurrentMap<String,ConcurrentMap<String, ChannelHandlerContext>> userChannelMap = UserWebSocketMultiMap.getInstance();
        ConcurrentMap<String,ChannelHandlerContext> channelMap;

        channelMap = (userChannelMap.get(userId)==null)?new ConcurrentHashMap<>():userChannelMap.get(userId);
        channelIdMap.put(channelId, userId);

        userChannelMap.put(userId,channelMap);
        return true;
    }
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        ConcurrentMap<String, String> channelIdMap = UserWithChannelIdMap.getInstance();
        ConcurrentMap<String, ConcurrentMap<String, ChannelHandlerContext>> userChannelMap = UserWebSocketMultiMap.getInstance();

        String channelId = ctx.channel().id().toString();
        String userId = "";

        if(channelIdMap.containsKey(channelId)) {
            userId = channelIdMap.get(channelId);
        }

        if(userChannelMap.containsKey(userId)) {
            ConcurrentMap<String, ChannelHandlerContext> channelMap = userChannelMap.get(userId);

            channelMap.remove(channelId);
            System.out.println("remove channel");
        }

        super.channelUnregistered(ctx);
    }
}
