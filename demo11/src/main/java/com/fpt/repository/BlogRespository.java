package com.fpt.repository;

import com.fpt.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRespository extends JpaRepository<Blog, Long> {
    Blog findBlogBySlug(String slug);

    List<Blog> findByNameContainingIgnoreCaseOrContentContainingIgnoreCase(String name, String content);
}
