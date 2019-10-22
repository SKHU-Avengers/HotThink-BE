package skhu.ht.hotthink.api.idea.model;

import skhu.ht.hotthink.api.domain.IdeaState;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

//unused
public class HotInDTO {
    private Long id_seq;
    private String title;
    @Enumerated(EnumType.STRING)
    private IdeaState state;
    private String contents;
    private String review;
}
