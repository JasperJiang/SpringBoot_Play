package com.example.demo.controller;

import com.example.demo.util.AppConstants;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.*;

@RestController
@RequestMapping("/file")
public class FileController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void fileUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {

            File desDir = new File(AppConstants.filePath);
            if (!desDir.exists()) {
                desDir.mkdir();
            }

            File desFile = new File(desDir.getAbsolutePath() + "/" + file.getOriginalFilename());
            try {
                file.transferTo(desFile);
            } catch (IOException e) {
                throw new RuntimeException("cannot upload file");
            }


        }
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response) {
        File file = new File("./files/resource.txt");
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        try (FileInputStream fis = new FileInputStream(file)){
            OutputStream os = response.getOutputStream();
            IOUtils.copyLarge(fis, os);
            os.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("file not found");
        } catch (IOException e) {
            throw new RuntimeException("error download file");
        }
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        File desDir = new File("./");
        return desDir.getAbsolutePath();
    }
}
