package skhu.ht.hotthink.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DomainMapper {
    @Autowired
    ModelMapper modelMapper;

    public <D,E> D convertToDomain(E source, Class<? extends D> classLiteral){
        return modelMapper.map(source,classLiteral);
    }
}
