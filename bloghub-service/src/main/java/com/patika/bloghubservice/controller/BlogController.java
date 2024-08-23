package com.patika.bloghubservice.controller;

import com.patika.bloghubservice.dto.request.BlogSaveRequest;
import com.patika.bloghubservice.dto.response.BlogResponse;
import com.patika.bloghubservice.dto.response.GenericResponse;
import com.patika.bloghubservice.model.Blog;
import com.patika.bloghubservice.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping("/users/{email}")
    public GenericResponse<BlogResponse> createBlog(@RequestBody BlogSaveRequest request, @PathVariable String email) {
        return GenericResponse.success(blogService.createBlog(email, request));
    }

    @GetMapping("{blogId}/users/{userId}")
    public ResponseEntity<GenericResponse<BlogResponse>> findBlogById(
            @PathVariable(name = "blogId") Long blogId,
            @PathVariable(name = "userId") Long userId) {

        return ResponseEntity.ok(blogService.getBlogById(blogId, userId));
    }

    @GetMapping
    public GenericResponse<List<Blog>> getAllBlogs() {
        return GenericResponse.success(blogService.getAll());
    }

    @GetMapping("/{title}")
    public Blog getBlogByEmail(@PathVariable String title) {
        return blogService.getBlogByTitle(title);
    }

    @PutMapping("/{title}/users/{email}")
    public void addComment(@PathVariable String title, @PathVariable String email, @RequestBody String comment) {
        blogService.addComment(title, email, comment);
    }

    @PutMapping("/{title}/users/{email}/like-count")
    public void likeBlog(@PathVariable String title, @PathVariable String email) {
        blogService.likeBlog(title, email);
    }

    @GetMapping("/{title}/like-count")
    public Long getLikeCountByTitle(@PathVariable String title) {
        return blogService.getLikeCountByTitle(title);
    }

    @GetMapping("/users/{email}/published-or-draft")
    public GenericResponse<List<Blog>> getAllByPublishedOrDraft(@PathVariable String email) {
        return GenericResponse.success(blogService.findAllByPublishedOrDraft(email));
    }

    @DeleteMapping("{title}/users/{email}")
    public void deleteBlog(String title, String email) {
        blogService.deleteBlog(title, email);
    }
    //commentleri getiren end-point

    //kullanıcı sadece kendi blog'larını gören endpoint

    // resim yükleme

    @PostMapping("{title}/users/{email}")
    public GenericResponse<String> uploadNewBlogImage(@PathVariable String title,
                                                   @PathVariable String email,
                                                   @RequestParam("image") MultipartFile imageFile) {

        return GenericResponse.success(blogService.updateBlogImage(title, email, imageFile));
    }
}
