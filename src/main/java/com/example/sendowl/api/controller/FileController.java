package com.example.sendowl.api.controller;


import com.example.sendowl.api.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/files")
public class FileController {

    private final FileService fileService;

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "파일 싱글 업로드", description = "single file upload api", security = { @SecurityRequirement(name = "bearerAuth") })
    @PostMapping(path = "single", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // consumes는 들어오는 데이터 타입 정의, produces는 반환타입
    public ResponseEntity<?> fileUpload(@RequestPart MultipartFile file) throws Exception {

        return new ResponseEntity(fileService.fileUpload(file), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "파일 멀티 업로드", description = "multiple files upload api", security = { @SecurityRequirement(name = "bearerAuth") })
    @PostMapping(path = "multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // 다중 파일 업로드
    public ResponseEntity<?> multipleFilesUpload(@RequestPart List<MultipartFile> files) throws Exception {

        return new ResponseEntity(fileService.multipleFilesUpload(files), HttpStatus.OK);
    }

}
