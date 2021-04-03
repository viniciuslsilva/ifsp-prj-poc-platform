package br.edu.ifsp.scl.prj.poc.platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FakeExternalServiceClient {
    private final String BASE_URL = "http://localhost:5000";
    private final RestTemplate restTemplate;

    @Autowired
    public FakeExternalServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String postFile(MultipartFile file) throws IOException {
        Path tempFile = Files.createTempFile(null, null);
        Files.write(tempFile, file.getBytes());
        File fileToSend = tempFile.toFile();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(fileToSend));

        String url = BASE_URL.concat("/v1/process");
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            return restTemplate.postForObject(url, request, String.class);
        } finally {
            fileToSend.delete();
        }
    }

    public Resource getFile(String filename) {
        String url = BASE_URL.concat("/v1/uploads/" + filename);
        return restTemplate.getForObject(url, ByteArrayResource.class);
    }
}
