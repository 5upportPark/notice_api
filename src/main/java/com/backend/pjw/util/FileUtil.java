package com.backend.pjw.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class FileUtil {
    private static final String PATH = "C:/flab_project/files/";

    private FileUtil(){
        throw new IllegalStateException("FileUtil is Utility Class");
    }

    public static String uploadFile(MultipartFile file){
        String uploadPath = getUploadPath()+File.separator+System.currentTimeMillis()+"_"+file.getOriginalFilename();
        Path path = null;
        try{
            path = Paths.get(uploadPath);
            Files.copy(file.getInputStream(), path);
        } catch (Exception e) {
            if(path != null)
                Files.deleteIfExists(path);
        } finally {
            return uploadPath;
        }
    }

    private static String getUploadPath(){
        LocalDate date = LocalDate.now();
        String uploadPath = PATH+date.getYear()+date.getMonthValue()+date.getDayOfMonth();
        File folder = new File(uploadPath);
        if(!folder.exists()) folder.mkdirs();
        return uploadPath;
    }
}
