package skhu.ht.hotthink.api.idea.model;


import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
public class Pagination {
    String category;
    int page;
    int size;
    int searchBy;
    String searchText;
    int orderBy;
    int recordCount;
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
