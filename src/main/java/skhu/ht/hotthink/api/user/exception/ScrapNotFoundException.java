package skhu.ht.hotthink.api.user.exception;

import skhu.ht.hotthink.error.exception.EntityNotFoundException;


public class ScrapNotFoundException extends EntityNotFoundException {
    public ScrapNotFoundException() {
        super("Scrap Not Found Exception");
    }
}
