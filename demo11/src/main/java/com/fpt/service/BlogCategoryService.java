package com.fpt.service;


import com.fpt.dto.BlogCategoryDto;
import com.fpt.model.BlogCategory;

import java.util.List;

public interface BlogCategoryService {
    BlogCategoryDto createBC(BlogCategory blogCategory);

    boolean deleteBC(Long id);

    BlogCategory findById(Long id);

    BlogCategory updateBC(Long id, BlogCategory blogCategory);

    List<BlogCategoryDto> listAllBlogCategory();

    BlogCategoryDto getBlogCategoryDtoById(Long id);

}

