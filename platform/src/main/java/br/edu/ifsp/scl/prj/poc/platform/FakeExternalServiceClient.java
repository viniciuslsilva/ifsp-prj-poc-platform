package br.edu.ifsp.scl.prj.poc.platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FakeExternalServiceClient {
    private final RestTemplate restTemplate;
    private final FakeExternalServiceProperties properties;

    private static final String V1_PROCESS_PATH = "/v1/process";
    private static final String V1_UPLOAD_PATH = "/v1/uploads";

    @Autowired
    public FakeExternalServiceClient(RestTemplate restTemplate, FakeExternalServiceProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    public String postFile(MultipartFile file) throws IOException {
        Path tempFile = Files.createTempFile(null, null);
        Files.write(tempFile, file.getBytes());
        File fileToSend = tempFile.toFile();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(fileToSend));

        URI uri = URI.create(properties.getHost() + ":" + properties.getPort() + V1_PROCESS_PATH);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            return restTemplate.postForObject(uri, request, String.class);
        } finally {
            fileToSend.delete();
        }
    }

    public Resource getFile(String filename) {
        URI uri = URI.create(properties.getHost() + ":" + properties.getPort() + V1_UPLOAD_PATH + "/" + filename);
        return restTemplate.getForObject(uri, ByteArrayResource.class);
    }
}
