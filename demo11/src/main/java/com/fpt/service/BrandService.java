package com.fpt.service;

import com.fpt.dto.BrandDto;
import com.fpt.model.Brand;

import java.util.List;

public interface BrandService {
    BrandDto createBrand(Brand brand);

    Brand findById(Long id);

    List<Brand> listAllBrand();

    boolean deleteBrand(Long id);

    Brand updateBrand(Long id, BrandDto brandDto);

    BrandDto findBrandDtoById(Long id);

    BrandDto getBrandDtoBySlug(String slug);
}
