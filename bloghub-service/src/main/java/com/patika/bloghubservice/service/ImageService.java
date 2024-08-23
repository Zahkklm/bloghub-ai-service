package com.patika.bloghubservice.service;

import com.patika.bloghubservice.dto.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class ImageService {

    public GenericResponse<String> uploadImage(MultipartFile imageFile) {
        if (imageFile.isEmpty()) {
            return GenericResponse.error("Resim boş olamaz.");
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String apiUrl = System.getenv("IMGBB_API_URL");
            String apiKey = System.getenv("IMGBB_API_KEY");
            HttpPost httpPost = new HttpPost(apiUrl + "?key=" + apiKey);

            Path tempFilePath = Files.createTempFile("temp", imageFile.getOriginalFilename());
            File tempFile = tempFilePath.toFile();
            imageFile.transferTo(tempFile);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addPart("image", new FileBody(tempFile, ContentType.DEFAULT_BINARY));
            HttpEntity multipart = builder.build();
            httpPost.setEntity(multipart);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                String responseString = EntityUtils.toString(responseEntity);

                if (response.getStatusLine().getStatusCode() != 200) {
                    return GenericResponse.error("Resim yüklenemedi: " + responseString);
                }

                JSONObject jsonObject = new JSONObject(responseString);
                String imageUrl = jsonObject.getJSONObject("data").getString("url");

                Files.delete(tempFilePath);

                return GenericResponse.success(imageUrl);
            }
        } catch (IOException e) {
            return GenericResponse.error("Resim yüklenemedi.");
        }
    }
}
