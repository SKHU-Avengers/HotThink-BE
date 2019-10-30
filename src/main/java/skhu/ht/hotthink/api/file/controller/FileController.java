package skhu.ht.hotthink.api.file.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import skhu.ht.hotthink.api.file.service.FileService;
import skhu.ht.hotthink.api.file.model.FileResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api")
public class FileController {
    @Autowired
    FileService fileService;

    /*
        작성일: 19-10-23
        작성자: 홍민석
        내용: 단일 파일 업로드 기능
        성공시 CREATED 반환.
     */
    @PostMapping("/image")
    public ResponseEntity<?> ImageUpload(@RequestParam("file") MultipartFile sourceFile){
        FileResponse response = upload(sourceFile);

        return new ResponseEntity(response ,HttpStatus.CREATED);
    }

    /*
        작성일: 19-10-23
        작성자: 홍민석
        내용: 복수의 파일 업로드.
        성공시 CREATED 반환.
     */
    @PostMapping("/images")
    public ResponseEntity<?> ImagesUpload(@RequestParam("files") MultipartFile[] sourceFiles) throws IOException{
        List<FileResponse> responses = Arrays.asList(sourceFiles)
                .stream()
                .map(file -> upload(file))
                .collect(Collectors.toList());
        return new ResponseEntity(responses, HttpStatus.CREATED);
    }
    /*
        작성일: 19-10-23
        작성자: 홍민석
        내용: 경로에 파일이름 입력시
        해당하는 파일이 존재하면 파일과 OK전달.
        찾을 수 없으면 NOT_FOUND 전달.
        클라이언트 측에서 요청시 content-type:multipart/form-data 헤더를 패킷에 포함하지 말것
     */
    @GetMapping("/image/{fileName:.+}")
    public ResponseEntity<Resource> ImageDownload(@PathVariable String fileName, HttpServletRequest request){
        //리소스로 파일 불러오기
        Resource resource = fileService.loadFileAsResource(fileName);

        //파일 종류 알아내기
        String contentType = null;
        try{
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch(IOException e){
            log.info("Could not determine file type.");
            return new ResponseEntity("Could not determine file type.",HttpStatus.NOT_FOUND);
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    /*
    작성일: 19-10-23
    작성자: 홍민석
    내용: 파일 업로드 수행 후 응답 반환
     */
    public FileResponse upload(MultipartFile sourceFile){
        String fileName = fileService.storeFile(sourceFile);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/image/")
                .path(fileName)
                .toUriString();
        FileResponse response = new FileResponse();
        response.setFileName(fileName);
        response.setAttachmentUrl(fileDownloadUri);
        response.setFileContentType(sourceFile.getContentType());
        response.setFileSize(sourceFile.getSize());
        return response;
    }
}
