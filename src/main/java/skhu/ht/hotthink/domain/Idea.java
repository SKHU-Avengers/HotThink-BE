package skhu.ht.hotthink.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Idea {
    @Id
    int seq;
    Category category;
    int hits;
    String head;
    String state;
    int score;
    int ss;
    Date date;
    String contents;
    String review;

}
