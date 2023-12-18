package com.fpt.repository;

import com.fpt.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findCategoryBySlug(String slug);

    Category findCategoryByName(String name);
}
