package skhu.ht.hotthink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import skhu.ht.hotthink.config.FileUploadConfig;
import skhu.ht.hotthink.netty.NettyServer;

@SpringBootApplication
@EnableConfigurationProperties({
        FileUploadConfig.class
})
public class HotthinkApplication {
    //Netty를 이용한 Push Server
    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(HotthinkApplication.class, args);//스프링 부트 스타트
        NettyServer nettyServer = context.getBean(NettyServer.class);
        nettyServer.start();//Netty 서버 스타트
    }

}
