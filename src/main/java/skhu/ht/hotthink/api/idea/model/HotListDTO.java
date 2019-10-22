package skhu.ht.hotthink.api.idea.model;

import skhu.ht.hotthink.api.domain.IdeaState;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class HotListDTO {
        private Long id_seq;
        private Long seq;
        private Integer hits;
        private String title;
        @Enumerated(EnumType.STRING)
        private IdeaState state;
        private Integer myScore;
        private Integer sellerScore;
        private String contents;
        private String review;
        private String pmaterial;

        private String category;
        private UserOutDTO user;
}
