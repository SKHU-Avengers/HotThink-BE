package skhu.ht.hotthink.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.logging.SocketHandler;

@Component
@PropertySource(value = "classpath:/push.properties")
public class PushServer {
    @Value("${tcp.port}")
    private int tcpPort;
    @Value("${boss.thread.count}")
    private int bossCount; //Boss의 수
    @Value("${worker.thread.count}")
    private int workerCount; //Worker의 수
    private static final ServiceHandler SERVICE_HANDLER = new ServiceHandler();
    //Start

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    public void start(){
        //논블로킹 이벤트 그룹
        EventLoopGroup eventGroup = new NioEventLoopGroup();
        //연결된 클라이언트로 부터 데이터 입출력을 담당하는 자식 스레드
        ChannelFuture channelFuture;
        //인바운드 핸들러
        InBoundHandler inBoundHandler = new InBoundHandler();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(eventGroup)
                    .channel(NioServerSocketChannel.class)//서버 소켓 입출력 모드를 NIO로 설정
                    .handler(new LoggingHandler(LogLevel.INFO))//서버 소켓 채널 핸들러 등록
                    .localAddress(tcpPort)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        /*
                            InBoundHandler를 pipeline에 추가
                         */
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(inBoundHandler);
                        }
                    });
            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync(); //서버를 비동기식으로 바인딩
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //bossGroup.shutdownGracefully().sync();
            try {
                eventGroup.shutdownGracefully().sync();
            }catch(Exception e){
                log.error(e.toString());
            }
        }
    }
}