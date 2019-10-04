package skhu.ht.hotthink.api.idea.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
public class Pagination {
    @Getter @Setter String category;
    @Getter @Setter int page;
    @Getter @Setter int size;
    @Getter @Setter int searchBy;
    @Getter @Setter String searchText;
    @Getter @Setter int orderBy;
    @Getter @Setter int recordCount;

    public String getQueryString() {
        String url = null;
        try{
            String temp = ( searchText == null ) ? "": URLEncoder.encode(searchText,"UTF-8");
            url = String.format("bd=%s&pg=%d&sz=%d&sb=%d&st=%s",category,page,size,orderBy,searchBy,temp);
        }catch(UnsupportedEncodingException e){

        }
        return url;
    }
}
