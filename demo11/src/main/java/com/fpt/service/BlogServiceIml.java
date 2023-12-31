package com.fpt.service;

import com.fpt.repository.BlogCategogyRepository;
import com.fpt.repository.BlogRespository;
import com.fpt.repository.UserRepository;
import com.fpt.dto.BlogDto;
import com.fpt.model.Blog;
import com.fpt.model.BlogCategory;
import com.fpt.model.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BlogServiceIml implements BlogService {
    private final BlogRespository blogRespository;
    private final BlogCategogyRepository blogCategogyRepository;
    private final UserRepository userRepository;

    public BlogServiceIml(BlogRespository blogRespository, BlogCategogyRepository blogCategogyRepository, UserRepository userRepository) {
        this.blogRespository = blogRespository;
        this.blogCategogyRepository = blogCategogyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Blog createBlog(BlogDto blogDto) {
        Blog bl = new Blog();
        bl.setName(blogDto.getName());
        bl.setContent(blogDto.getContent());
        Date date = new Date();
        long timestamp = date.getTime();
        bl.setSlug(blogDto.getSlug() + "-" + timestamp);
        bl.setImage(blogDto.getImage());
        bl.setCreatedAt(new Date());
        bl.setShortContent(blogDto.getShortContent());
        BlogCategory bc = blogCategogyRepository.findById(blogDto.getCategoryId()).orElse(new BlogCategory());
        bl.setBlogCategory(bc);
        User u = userRepository.findByUsername(blogDto.getUserName());
        bl.setUser(u);
        blogRespository.save(bl);
        return bl;
    }

    @Override
    public boolean deleteBlog(Long id) {
        Optional<Blog> blogOp = blogRespository.findById(id);
        if (blogOp.isPresent()) {
            Blog bc = blogOp.get();
            blogRespository.delete(bc);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateBlog(Long id, BlogDto blog) {
        Optional<Blog> blogOp = blogRespository.findById(id);
        BlogCategory category = blogCategogyRepository.findById(blog.getCategoryId()).orElse(null);
        if (blogOp.isPresent() && category != null) {
            Blog bl = blogOp.get();
            bl.setName(blog.getName());
            bl.setContent(blog.getContent());
            bl.setImage(blog.getImage());
            bl.setCreatedAt(blog.getCreatedAt());
            bl.setShortContent(blog.getShortContent());
            bl.setBlogCategory(category);
            Date date = new Date();
            long timestamp = date.getTime();
            bl.setSlug(blog.getSlug() + "-" + timestamp);
            blogRespository.save(bl);
            return true;
        }
        return false;
    }

    @Override
    public BlogDto getBlogBySlug(String slug) {
        try {
            Blog blog = blogRespository.findBlogBySlug(slug);
            BlogDto blogDto = blogDtoConvert(blog);
            return blogDto;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<BlogDto> getAllBlog() {
        try {
            List<Blog> blogs = blogRespository.findAll();
            List<BlogDto> blogDtoList = new ArrayList<>();
            for (Blog blog: blogs) {
                BlogDto blogDto = blogDtoConvert(blog);
                blogDtoList.add(blogDto);
            }
            Collections.reverse(blogDtoList);
            return blogDtoList;
        } catch (Exception e) {
            return null;
        }
    }

    private BlogDto blogDtoConvert(Blog blog) {
        BlogDto blogDto = new BlogDto();
        blogDto.setId(blog.getId());
        blogDto.setUserName(blog.getUser().getUsername());
        blogDto.setName(blog.getName());
        blogDto.setContent(blog.getContent());
        blogDto.setImage(blog.getImage());
        blogDto.setCreatedAt(blog.getCreatedAt());
        blogDto.setShortContent(blog.getShortContent());
        blogDto.setSlug(blog.getSlug());
        blogDto.setCategoryId(blog.getBlogCategory().getId());
        return blogDto;
    }
}
