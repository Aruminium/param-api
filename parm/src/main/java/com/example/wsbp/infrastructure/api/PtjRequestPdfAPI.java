package com.example.wsbp.infrastructure.api;

import com.example.wsbp.domain.models.request.PtjRequest;
import org.apache.commons.io.FileUtils;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;


public class PtjRequestPdfAPI {
    private static final ResourceBundle rb = ResourceBundle.getBundle("application");
    private final static String URL = rb.getString("param.api.url");
    private final static String PDF_DIRECTORY = "src/main/resources/pdfs/";
    private final HttpHeaders headers = new HttpHeaders();
    private final PtjRequest ptjRequest;
    private String fileName = "";

    public PtjRequestPdfAPI(PtjRequest ptjRequest) throws IOException {
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setCacheControl(CacheControl.noStore());
        this.ptjRequest = ptjRequest;
    }

    public File getFile() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(ptjRequest.mapperJson(), headers);
        ResponseEntity<byte[]> entity = restTemplate.exchange(URL, HttpMethod.POST, request, byte[].class);
        fileName = entity.getHeaders().getContentDisposition().getFilename();
        String path = PDF_DIRECTORY + fileName;
        FileUtils.writeByteArrayToFile(new File(path), Objects.requireNonNull(entity.getBody()));

        return new File(path);
    }

    public void emptyDirectory() throws IOException {
        File directory = new File(PDF_DIRECTORY);
        FileUtils.cleanDirectory(directory);
    }

    public String getFileName() {
        return fileName;
    }

}
