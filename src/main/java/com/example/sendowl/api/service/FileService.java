package com.example.sendowl.api.service;

import com.example.sendowl.domain.file.dto.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    public String fileUpload(MultipartFile file) throws Exception{
        if(!file.isEmpty()){
            // 경로 지정, (없을 시 폴더 생성)
            String filePath = "C:\\Temp\\upload";
            File fileDir = new File(filePath);
            if(!fileDir.exists()){
                fileDir.mkdir();
            }

            FileDto.FileRes dto = new FileDto.FileRes(UUID.randomUUID().toString(),
                    file.getOriginalFilename(),
                    file.getContentType());
            System.err.println(file);
            System.err.println(file.getOriginalFilename());

            File newFileName = new File(filePath,dto.getUuid() + "_" +dto.getFileName());
            file.transferTo(newFileName);

            return dto.getFileName();
        } else {
            return null;
        }
    }
}
