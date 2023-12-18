package com.fpt.repository;

import com.fpt.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Brand findBrandBySlug(String slug);

    Brand findBrandByName(String name);
}
