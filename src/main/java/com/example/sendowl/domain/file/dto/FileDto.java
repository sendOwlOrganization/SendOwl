package com.example.sendowl.domain.file.dto;

import lombok.Getter;

public class FileDto {
    @Getter
    public static class FileRes {
        private String uuid;
        private String fileName;
        private String contentType;

        public FileRes() {};

        public FileRes(String uuid, String fileName, String contextType){
            this.uuid = uuid;
            this.fileName = fileName;
            this.contentType = contextType;
        }

    }
}
