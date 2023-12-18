package com.fpt.service;

import com.fpt.dto.SearchDto;

import java.util.List;

public interface SearchService {
    List<SearchDto> searchLaptopByTitle(String keyword);

    List<SearchDto> searchBogByTitle(String keyword);
}
