package skhu.ht.hotthink.api.idea.model;

import lombok.Getter;
import lombok.Setter;

public class Option {
    @Getter @Setter private int value;
    @Getter @Setter private String label;

    public Option(int value, String label){
        this.value = value;
        this.label = label;
    }


}
