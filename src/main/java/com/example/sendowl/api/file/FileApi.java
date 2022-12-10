package com.example.sendowl.api.file;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileApi {

    public String makeFileFolder() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sdf.format(date);

        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "/service/data/" + str.replace("-", "/");
        } else if (os.contains("mac")) {
            return "/service/data/" + str.replace("-", "/");
        } else { // linux
            return "/service/data/" + str.replace("-", "/");
        }
    }
}
