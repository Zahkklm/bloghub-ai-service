package com.patika.bloghubservice.dto.response;

import com.patika.bloghubservice.model.BlogComment;
import com.patika.bloghubservice.model.enums.BlogStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogResponse {

    String title;
    String text;
    LocalDateTime createdDateTime;
    BlogStatus blogStatus;
    Long likeCount;
    List<BlogCommentResponse> blogCommentList = new ArrayList<>();

}
