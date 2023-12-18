package com.fpt.service;

import com.fpt.dto.CategoryDto;
import com.fpt.model.Category;
import java.util.List;
public interface CategoryService {
     CategoryDto createCategory(Category category);

     Category findById(Long id);

     List<Category> listAll();

     boolean deleteCategoryById(Long id);

     Category updateCategoryById(Long id, Category category);

     CategoryDto getCategoryDtoBySlug(String slug);
}
