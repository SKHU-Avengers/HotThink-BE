package skhu.ht.hotthink.api.idea.exception;

import skhu.ht.hotthink.error.exception.EntityNotFoundException;
import skhu.ht.hotthink.error.ErrorCode;

public class IdeaNotFoundException extends EntityNotFoundException {
    public IdeaNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
    public IdeaNotFoundException() {
        super(ErrorCode.ENTITY_NOT_FOUND);
    }
}
