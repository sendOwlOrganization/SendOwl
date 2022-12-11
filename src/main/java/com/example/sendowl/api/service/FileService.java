package com.example.sendowl.api.service;

import com.example.sendowl.api.file.FileApi;
import com.example.sendowl.domain.file.dto.FileDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    public String fileUpload(MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            // 경로 지정, (없을 시 폴더 생성)
            FileApi fa = new FileApi();
            String filePath = fa.makeFileFolder();
            File fileDir = new File(filePath);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }

            FileDto.FileRes dto = new FileDto.FileRes(UUID.randomUUID().toString(),
                    file.getOriginalFilename(),
                    file.getContentType());

            File newFileName = new File(fileDir.getAbsolutePath()
                    , dto.getUuid() + "_" + dto.getFileName());
            file.transferTo(newFileName); // 인자로 주는 파일에 데이터를 옮긴다.

            return newFileName.getAbsolutePath();
        } else {
            return null;
        }
    }

    public List<String> multipleFilesUpload(List<MultipartFile> files) throws Exception {
        List<String> fileNames = new ArrayList<String>();

        // 경로 지정, (없을 시 폴더 생성)
        FileApi fa = new FileApi();
        String filePath = fa.makeFileFolder();
        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {

                FileDto.FileRes dto = new FileDto.FileRes(UUID.randomUUID().toString(),
                        file.getOriginalFilename(),
                        file.getContentType());

                File newFileName = new File(fileDir.getAbsolutePath(), dto.getUuid() + "_" + dto.getFileName());
                file.transferTo(newFileName);

                fileNames.add(file.getOriginalFilename());
            } else {
                continue;
            }
        }

        return fileNames;
    }

    public byte[] getImage(String path) throws IOException {
        // path 에 대한 valide 확인

        InputStream in = new FileInputStream("/service/data/" + path);
        return IOUtils.toByteArray(in);
    }
}
