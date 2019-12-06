package skhu.ht.hotthink.api.file.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import skhu.ht.hotthink.api.file.exception.FileDownloadException;
import skhu.ht.hotthink.api.file.exception.FileUploadException;
import skhu.ht.hotthink.config.FileUploadConfig;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {
    private static final Logger log = LoggerFactory.getLogger(FileService.class);
    private Path fileLocation;

    @Autowired
    public FileService(FileUploadConfig prop){
        this.fileLocation = Paths.get(prop.getUploadDir())
                .toAbsolutePath().normalize();

        try{
            Files.createDirectories(this.fileLocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String storeFile(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try{
            if(fileName.contains("..")) {
                throw new FileUploadException("파일을 업로드 할 수 없습니다.");
            }
            Path targetLocation = this.fileLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        }catch(Exception e){
            throw new FileUploadException("파일을 업로드 할 수 없습니다.",e);
        }
    }

    public Resource loadFileAsResource(String fileName){
        try{
            Path filePath = this.fileLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            log.info("File Path : ".concat(resource.getURI().toString()));
            if(resource.exists()){
                return resource;
            }else{
                throw new FileDownloadException(fileName + "파일을 찾을 수 없습니다.");
            }
        }catch(MalformedURLException e){
            throw new FileDownloadException(fileName + "파일을 찾을 수 없습니다.(잘못된 URL 형식)");
        } catch (IOException e) {
            throw new FileDownloadException("URI 에러");
        }
    }

}
