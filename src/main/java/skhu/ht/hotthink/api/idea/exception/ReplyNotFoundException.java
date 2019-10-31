package skhu.ht.hotthink.api.idea.exception;

import skhu.ht.hotthink.error.exception.EntityNotFoundException;

public class ReplyNotFoundException extends EntityNotFoundException {
    public ReplyNotFoundException() {
        super("Reply Not Found");
    }
}
