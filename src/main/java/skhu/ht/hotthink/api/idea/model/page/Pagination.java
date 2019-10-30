package skhu.ht.hotthink.api.idea.model.page;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.lang.Nullable;
import skhu.ht.hotthink.api.domain.enums.BoardType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Null;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
public class Pagination {
    String category;
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    BoardType boardType;
    int page;
    int size;
    int searchBy;
    @Nullable
    String searchText;
    int orderBy;
    /*
    public String getQueryString() {
        String url = null;
        try{
            String temp = ( searchText == null ) ? "": URLEncoder.encode(searchText,"UTF-8");
            url = String.format("bd=%s&pg=%d&sz=%d&sb=%d&st=%s",category,page,size,orderBy,searchBy,temp);
        }catch(UnsupportedEncodingException e){

        }
        return url;
    }
     */
}
