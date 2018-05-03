package ru.reksoft.onlineShop.service;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import ru.reksoft.onlineShop.storage.StorageException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageService {
    private static final Path ROOT_LOCATION = Paths.get("src/main/resources/static/img");


    public String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        Path path =ROOT_LOCATION.resolve(Long.toString(System.currentTimeMillis())+'.'+getFileExtension(filename));
        try {
//            File img = path.toFile();
//            String newName = Long.toString(System.currentTimeMillis());
//            path.toFile().renameTo(new File(newName));

//            file.transferTo(new File("q.png"));
//           FileItem fileItem = new DiskFileItem("file", "text/plain", false, file.getName(), (int) file.length() , file.getParentFile());
//          MultipartFile f=new CommonsMultipartFile(fileItem);

            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return path.getFileName().toString();
        } catch (IOException e) {
            throw new StorageException("Failed to store file " +filename, e);
        }
    }

    private String getFileExtension(String filename){
       return  filename.substring( filename.lastIndexOf('.')+1, filename.length());
    }
}
