package com.fpt.repository;

import com.fpt.model.BlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogCategogyRepository extends JpaRepository<BlogCategory, Long> {
    BlogCategory findBlogCategoryByName(String name);
}
