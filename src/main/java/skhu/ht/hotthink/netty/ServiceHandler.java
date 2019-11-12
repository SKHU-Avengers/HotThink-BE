package skhu.ht.hotthink.netty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skhu.ht.hotthink.netty.model.UserWebSocketMultiMap;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;


/**
 * The type Service handler.
 */
@ChannelHandler.Sharable
public class ServiceHandler extends SimpleChannelInboundHandler<FullHttpMessage> {
    /**
     * The Channels.
     */
    private final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * Channel active.
     *
     * @param ctx the ctx
     * @throws Exception the exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpMessage fullHttpMsg) throws Exception {
        ConcurrentMap<String, ConcurrentMap<String, ChannelHandlerContext>> userChannelMap = UserWebSocketMultiMap.getInstance();
        ObjectMapper objectMapper = new ObjectMapper();
        HttpRequest httpRequest = null;
        if(fullHttpMsg instanceof HttpRequest){
            httpRequest = (HttpRequest)fullHttpMsg;
        }

        String uri = httpRequest.uri();
        Map<String,String> paramMap = paramGetter(uri);
        String userId = paramMap.get("userId");

        String jsonResult = URLDecoder.decode(objectMapper.writeValueAsString(paramMap), "UTF-8");

        if(userChannelMap.containsKey(userId)){
            ConcurrentMap<String, ChannelHandlerContext> channelMap = userChannelMap.get(userId);
            Set<String> keySets = channelMap.keySet();
            for(String key: keySets){
                WebSocketFrame sockFrame= new TextWebSocketFrame(jsonResult);
                ChannelHandlerContext chc = channelMap.get(key);
                chc.fireChannelActive();

                chc.channel().writeAndFlush(sockFrame);
            }
        }
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer("success", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE,"applications/json;charset=utf-8");
        response.headers().set("Access-Control-Allow-Origin","null");
        response.headers().set("Access-Control-Allow-Methods","POST,GET,OPTIONS,DELETE");
        response.headers().set("Access-Control-Max-Age","3600");
        response.headers().set("Access-Control-Allow-Headers","Content-Type, Accept, X-Requested-With, remember-me");
        response.headers().set("Access-Control-Allow-Credentials","true");
        response.headers().set("Access-Control-Expose-Headers","Access-Control-Allow-Origin,Access-Control-Allow-Credentials");
        //연결종료
        ctx.write(response).addListener(ChannelFutureListener.CLOSE);
    }

    private Map<String, String> paramGetter(String uri) throws UnsupportedEncodingException {
        Map<String, String> paramMap = new HashMap<>();
        String params = uri.substring(uri.indexOf("?") + 1, uri.length());
        String[] paramsArray = params.split("&");

        for(String param : paramsArray) {
            String[] paramArray = param.split("=");

            if(paramArray[0].equals("content")) {
                String content = URLDecoder.decode(paramArray[1], "UTF-8");

                paramMap.put(paramArray[0], content);
            }

            paramMap.put(paramArray[0], paramArray[1]);
        }

        return paramMap;
    }
}

