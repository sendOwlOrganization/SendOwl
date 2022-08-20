package com.example.sendowl.api.file;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileApi {
    public String getFolder(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sdf.format(date);
        return str.replace("-", File.separator);
    }
}
