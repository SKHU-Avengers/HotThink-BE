package skhu.ht.hotthink.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
public class CommonBean {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
