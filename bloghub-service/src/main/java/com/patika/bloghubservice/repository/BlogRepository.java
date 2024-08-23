package com.patika.bloghubservice.repository;

import com.patika.bloghubservice.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    Optional<Blog> findByTitle(String title);
    void deleteByTitle(String title);
}
