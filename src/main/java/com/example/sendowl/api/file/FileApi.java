package com.example.sendowl.api.file;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileApi {

    public String makeFileFolder(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sdf.format(date);

        String filePath = "C:\\Temp\\upload\\" + str.replace("-", "\\");

        return filePath;
    }
}
