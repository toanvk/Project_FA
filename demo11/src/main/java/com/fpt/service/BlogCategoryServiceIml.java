package com.fpt.service;

import com.fpt.repository.BlogCategogyRepository;
import com.fpt.repository.BlogRespository;
import com.fpt.dto.BlogCategoryDto;
import com.fpt.dto.BlogDto;
import com.fpt.model.Blog;
import com.fpt.model.BlogCategory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BlogCategoryServiceIml implements BlogCategoryService {
    public final BlogCategogyRepository blogCategogyRepository;
    public  final BlogRespository blogRespository;
    public BlogCategoryServiceIml(BlogCategogyRepository blogCategogyRepository, BlogRespository blogRespository) {
        this.blogCategogyRepository = blogCategogyRepository;
        this.blogRespository = blogRespository;
    }

    @Override
    public BlogCategoryDto createBC(BlogCategory blogCategory) {
        BlogCategory bc = new BlogCategory();
        bc.setName(blogCategory.getName());
        bc.setContent(blogCategory.getContent());
        blogCategogyRepository.save(bc);
        BlogCategoryDto dto = convertToCategoryDTO(bc);
        return dto;
    }

    @Override
    public boolean deleteBC(Long id) {
        try {
            BlogCategory blogCategoryOp = blogCategogyRepository.findById(id).orElse(null);
            if (blogCategoryOp.getBlogs().size() > 0) {
                Set<Blog> blogs = blogCategoryOp.getBlogs();

                BlogCategory category = blogCategogyRepository.findBlogCategoryByName("Uncategorized");
                if (category != null) {
                    for (Blog b : blogs) {
                        b.setBlogCategory(category);
                        blogRespository.save(b);
                    }
                } else {
                    BlogCategory bnew = new BlogCategory();
                    bnew.setName("Uncategorized");
                    bnew.setContent("Uncategorized");
                    blogCategogyRepository.save(bnew);
                    for (Blog b : blogs) {
                        b.setBlogCategory(bnew);
                        blogRespository.save(b);
                    }
                }
            }
            blogCategogyRepository.delete(blogCategoryOp);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public BlogCategory findById(Long id) {
        Optional<BlogCategory> BlogcategoryOp = blogCategogyRepository.findById(id);
        if (BlogcategoryOp.isPresent()) {
            BlogCategory bc = BlogcategoryOp.get();
            return bc;
        }
        return null;
    }

    @Override
    public BlogCategory updateBC(Long id, BlogCategory blogCategory) {
        Optional<BlogCategory> updateOp = blogCategogyRepository.findById(id);
        if (updateOp.isPresent()) {
            BlogCategory bc = updateOp.get();
            bc.setName(blogCategory.getName());
            bc.setBlogs(blogCategory.getBlogs());
            bc.setContent(blogCategory.getContent());
            blogCategogyRepository.save(bc);
            return bc;
        }
        return null;
    }

    @Override
    public List<BlogCategoryDto> listAllBlogCategory() {
        try {
            List<BlogCategoryDto> blogCategoryDtoList = new ArrayList<>();
            List<BlogCategory> blogCategoryList = blogCategogyRepository.findAll();
            for (BlogCategory b : blogCategoryList) {
                blogCategoryDtoList.add(convertToCategoryDTO(b));
            }
            return blogCategoryDtoList;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public BlogCategoryDto getBlogCategoryDtoById(Long id) {
        BlogCategory bc = blogCategogyRepository.findById(id).orElse(null);
        if (bc != null) {
            BlogCategoryDto blogCategoryDto = convertToCategoryDTO(bc);
            return blogCategoryDto;
        }
        return null;
    }

    private BlogCategoryDto convertToCategoryDTO(BlogCategory category) {
        BlogCategoryDto dto = new BlogCategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setContent(category.getContent());
        Set<BlogDto> blogDTOs = new HashSet<>();
//        Set<Blog> blogs = category.getBlogs();
//        for (Blog blog: blogs) {
//            blogDTOs.add(convertToBlogDto(blog));
//        }
        dto.setBlogDtos(blogDTOs);
        return dto;
    }

    private BlogDto convertToBlogDto(Blog blog) {
        BlogDto dto = new BlogDto();
        dto.setId(blog.getId());
        dto.setUserName(blog.getUser().getName());
        dto.setName(blog.getName());
        dto.setContent(blog.getContent());
        dto.setImage(blog.getImage());
        dto.setCreatedAt(blog.getCreatedAt());
        dto.setShortContent(blog.getShortContent());
        dto.setSlug(blog.getSlug());
        dto.setCategoryId(blog.getBlogCategory().getId());
        return dto;
    }

}
