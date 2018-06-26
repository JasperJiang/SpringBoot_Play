package com.example.demo.controller;

import com.example.demo.util.AppConstants;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
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
import java.net.MalformedURLException;

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
    public ResponseEntity<Resource> downloadFile(HttpServletResponse response) {
        File file = new File("./files/resource.txt");
        try {
            Resource resource = new UrlResource(file.toURI());
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        File desDir = new File("./");
        return desDir.getAbsolutePath();
    }
}
