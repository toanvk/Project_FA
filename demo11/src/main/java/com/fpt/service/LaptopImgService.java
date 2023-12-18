package com.fpt.service;

import com.fpt.dto.LaptopImgDto;
import com.fpt.model.LaptopImg;

import java.util.List;

public interface LaptopImgService {
    LaptopImgDto create(LaptopImgDto laptopImg);

    void createMultiple(LaptopImgDto laptopImg);

    List<LaptopImgDto> listAllImg(Long id);

    boolean deleteImgById(Long id);

    LaptopImg findById(Long id);
}
