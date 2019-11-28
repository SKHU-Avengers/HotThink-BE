package skhu.ht.hotthink.netty.old;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.cors.CorsConfig;
import io.netty.handler.codec.http.cors.CorsConfigBuilder;
import io.netty.handler.codec.http.cors.CorsHandler;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class HttpServerInit extends ChannelInitializer<SocketChannel> {
    private static final CorsConfig corsConfig = CorsConfigBuilder.forAnyOrigin().allowNullOrigin().allowCredentials().build();
    @Override
    protected void initChannel(SocketChannel sockCh) throws Exception {
        ChannelPipeline pipeline = sockCh.pipeline();
        pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new CorsHandler(corsConfig));
        pipeline.addLast(new WebSocketServerProtocolHandler("/"));
        pipeline.addLast(new ServiceHandler());
        pipeline.addLast(new WebSocketHandler());
    }
}
