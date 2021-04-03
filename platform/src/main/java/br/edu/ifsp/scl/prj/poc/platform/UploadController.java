package br.edu.ifsp.scl.prj.poc.platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadController {

    private final FakeExternalServiceClient client;

    @Autowired
    public UploadController(FakeExternalServiceClient client) {
        this.client = client;
    }

    @PostMapping(value = "/v1/uploads")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        return client.postFile(file);
    }

    @PostMapping(value = "/v1/uploads/current")
    public ResponseEntity<Resource> uploadAndDownload(@RequestParam("file") MultipartFile file) throws IOException {
        String filename = client.postFile(file);
        return ResponseEntity.ok(client.getFile(filename));
    }

    @GetMapping("/v1/uploads/{filename}")
    public ResponseEntity<Resource> uploadStatus(@PathVariable("filename") String filename) {
        return ResponseEntity.ok(client.getFile(filename));
    }
}
