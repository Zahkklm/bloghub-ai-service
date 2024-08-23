package com.patika.bloghubservice.client.email;

import com.patika.bloghubservice.dto.request.EmailCreateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "email-service", path = "/api/v1/emails")
public interface EmailServiceClient {

    @PostMapping
    void sendEmail(@RequestBody EmailCreateRequest request);
}
