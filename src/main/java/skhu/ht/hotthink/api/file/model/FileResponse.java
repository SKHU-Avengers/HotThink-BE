package skhu.ht.hotthink.api.file.model;

import lombok.Data;

@Data
public class FileResponse {
    private String fileName;
    private Long fileSize;
    private String fileContentType;
    private String attachmentUrl;
}
