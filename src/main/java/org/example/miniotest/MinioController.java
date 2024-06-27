package org.example.miniotest;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;

@RestController
@AllArgsConstructor
public class MinioController {

    private MinioService minioService;

    @GetMapping("/files/{objectName}")
    public ResponseEntity<byte[]> getFile(@PathVariable String objectName) {
        System.out.println("Getting file " + objectName);
        try {
            InputStream inputStream = minioService.getFile(objectName);
            byte[] content = inputStream.readAllBytes();
            inputStream.close();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + objectName + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(content);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/files")
    public ResponseEntity<String> uploadFile() {
        minioService.addFile();
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }



}
