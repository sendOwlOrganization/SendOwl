package com.example.sendowl.api.controller;


import com.example.sendowl.api.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/files")
public class FileController {

    private final FileService fileService;

    @Operation(summary = "file upload api", description = "file upload api")
    @PostMapping(path = "upload") // 파일 업로드 테스트
    public ResponseEntity<?> fileUpload(@RequestPart MultipartFile file) throws Exception {

        String fileName = fileService.fileUpload(file);

        return new ResponseEntity(fileName, HttpStatus.OK);
    }

}
