package skhu.ht.hotthink.api.domain.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
public enum BoardType {
    HOT("hotthink"),
    FREE("freethink"),
    REAL("realthink"),
    REPLY("reply");
    @Getter private String url;

    BoardType(String url) {
        this.url = url;
    }
    public static BoardType getBoardType(String url){
        for(BoardType board:BoardType.class.getEnumConstants())
            if(board.getUrl()==url){return board;}
        return null;
    }
}
