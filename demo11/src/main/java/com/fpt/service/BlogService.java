package com.fpt.service;

import com.fpt.dto.BlogDto;
import com.fpt.model.Blog;




import java.util.List;


public interface BlogService {
    Blog createBlog(BlogDto blog);

    boolean deleteBlog(Long id);

    boolean updateBlog(Long id, BlogDto blog);

    BlogDto getBlogBySlug(String slug);

    List<BlogDto> getAllBlog();
}
