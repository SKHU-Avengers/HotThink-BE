package skhu.ht.hotthink.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="file")
public class FileUploadConfig {
    @Getter
    @Setter
    private String uploadDir;

}
