package com.patika.bloghubservice.service;

import com.patika.bloghubservice.client.email.EmailServiceClient;
import com.patika.bloghubservice.converter.BlogCommentConverter;
import com.patika.bloghubservice.converter.BlogConverter;
import com.patika.bloghubservice.dto.request.BlogSaveRequest;
import com.patika.bloghubservice.dto.response.BlogResponse;
import com.patika.bloghubservice.dto.response.GenericResponse;
import com.patika.bloghubservice.exception.BlogHubException;
import com.patika.bloghubservice.exception.ExceptionMessages;
import com.patika.bloghubservice.model.Blog;
import com.patika.bloghubservice.model.BlogComment;
import com.patika.bloghubservice.model.User;
import com.patika.bloghubservice.model.enums.BlogStatus;
import com.patika.bloghubservice.model.enums.StatusType;
import com.patika.bloghubservice.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserService userService;
    private final ImageService imageService;
    private final EmailServiceClient emailServiceClient;

    public BlogResponse createBlog(String email, BlogSaveRequest request) {
        User foundUser = userService.validateUser(email);

        if (!foundUser.getStatusType().equals(StatusType.APPROVED)) {
            throw new BlogHubException(ExceptionMessages.UNVERIFIED_EMAIL);
        }

        Blog blog = new Blog(request.getTitle(), request.getText(), foundUser);

        blogRepository.save(blog);

        return BlogConverter.toResponse(blog);
    }

    public Blog getBlogByTitle(String title) {
        return blogRepository.findByTitle(title).orElseThrow(() -> new RuntimeException("blog bulunamadı"));
    }

    public GenericResponse<BlogResponse> getBlogById(Long blogId, Long userId) {
        User user = userService.validateUserById(userId);

        Optional<Blog> foundBlog = user.getBlogList().stream()
                        .filter(blog -> blog.getId().equals(blogId))
                        .findFirst();

        if (foundBlog.isPresent()) {
            Blog blog = foundBlog.get();

            BlogResponse blogResponse = BlogResponse.builder()
                    .blogCommentList(BlogCommentConverter.toResponse(blog.getBlogCommentList()))
                    .createdDateTime(blog.getCreatedDate())
                    .text(blog.getText())
                    .likeCount(blog.getLikeCount())
                    .title(blog.getTitle())
                    .blogStatus(blog.getBlogStatus())
                    .build();

            return GenericResponse.success(blogResponse);
        }

        throw new BlogHubException(userId + ": Bu id'ye sahip kullaniciya ait verilen blog bulunamadi.");
    }

    public void addComment(String title, String email, String comment) {
        Blog foundBlog = getBlogByTitle(title);

        User user = userService.validateUser(email);

        BlogComment blogComment = new BlogComment(user, comment);

        foundBlog.getBlogCommentList().add(blogComment);

        blogRepository.save(foundBlog);

    }

    public List<Blog> getBlogsFilterByStatus(BlogStatus blogStatus, String email) {
        User foundUser = userService.validateUser(email);

        return foundUser.getBlogList().stream()
                .filter(blog -> blogStatus.equals(blog.getBlogStatus()))
                .toList();
    }

    public void changeBlogStatus(BlogStatus blogStatus, String title) {
        Blog foundBlog = getBlogByTitle(title);

        if (foundBlog.getBlogStatus().equals(BlogStatus.PUBLISHED)) {
            throw new RuntimeException("statüsü PUBLISHED olan bir blog silinemez.");
        }

        foundBlog.setBlogStatus(blogStatus);
    }

    public List<Blog> getAll() {
        return blogRepository.findAll();
    }

    public void likeBlog(String title, String email) {
        User founduser = userService.validateUser(email);
        Blog blog = blogRepository.findByTitle(title).orElseThrow(() -> new BlogHubException("Blog bulunamadi"));

        founduser.getBlogTitleLikeCountMap().compute(blog, (blogEntity, count) -> {
            if (count != null && count >= 50) {
                throw new BlogHubException(ExceptionMessages.MAXIMUM_LIKE_LIMIT_REACHED
                        + " blog title: " + blogEntity.getTitle());
            }

            return (count == null) ? 1 : count + 1;
        });

        blog.setLikeCount(blog.getLikeCount() + 1);

        blogRepository.save(blog);
    }

    public Long getLikeCountByTitle(String title) {

        Blog blog = getBlogByTitle(title);

        return blog.getLikeCount();
    }

    public List<Blog>   findAllByPublishedOrDraft(String email) {
        User founduser = userService.validateUser(email);

        return founduser.getBlogList().stream()
                .filter(blog -> blog.getBlogStatus() == BlogStatus.PUBLISHED
                || blog.getBlogStatus().equals(BlogStatus.DRAFT))
                .toList();
    }

    public void deleteBlog(String title, String email) {
        User foundUser = userService.validateUser(email);
        Optional<Blog> blogOptional = blogRepository.findByTitle(title);

        if (blogOptional.isPresent() && blogOptional.get().getUser().equals(foundUser)) {
            Blog blog = blogOptional.get();

            if (blog.getBlogStatus() == BlogStatus.DRAFT) {
                blogRepository.deleteByTitle(title);
            } else {
                blog.setBlogStatus(BlogStatus.DELETED);
                blogRepository.save(blog);
            }
        }
    }

    public String updateBlogImage(String title, String email, MultipartFile imageFile) {
        User foundUser = userService.validateUser(email);

        Optional<Blog> blogToUpdate = blogRepository.findAll().stream()
                .filter(blog -> blog.getTitle().equals(title) && blog.getUser().getEmail().equals(email))
                .findFirst();

        if (blogToUpdate.isPresent()) {
            GenericResponse<String> imageUploadResponse = imageService.uploadImage(imageFile);

            String newImageUrl = imageUploadResponse.getData();

            Blog blog = blogToUpdate.get();
            blog.setImageUrl(newImageUrl);
            blogRepository.save(blog);

            return "Resim başarıyla kaydedildi: " + imageUploadResponse.getData();
        }

        return foundUser.getEmail() +  "--> Bu kullanıcı için '" + title + "' başlıklı blog bulunamadı";
    }
}
