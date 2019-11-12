package skhu.ht.hotthink.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InBoundHandler extends ChannelInboundHandlerAdapter{
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    /*
        메시지가 들어올때마다 호출
        메시지를 콘솔에 로깅
    */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf in = (ByteBuf) msg;
        log.info("Netty Received: ".concat(in.toString(CharsetUtil.UTF_8)));
    }

    /*
    channelRead 의 마지막 호출에서 현재 일괄 처리의 마지막 메시지를 처리했음을 핸들러에 통보.
    대기중인 메시지를 원격 피어로 플러시하고 채널을 닫음.
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }
}
