package skhu.ht.hotthink.api.file.exception;

import skhu.ht.hotthink.error.ErrorCode;
import skhu.ht.hotthink.error.exception.BusinessException;

public class FileDownloadException extends BusinessException{
    public FileDownloadException(String message){
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}
