package skhu.ht.hotthink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import skhu.ht.hotthink.config.FileUploadConfig;

@SpringBootApplication
@EnableConfigurationProperties({
        FileUploadConfig.class
})
public class HotthinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotthinkApplication.class, args);
    }

}
