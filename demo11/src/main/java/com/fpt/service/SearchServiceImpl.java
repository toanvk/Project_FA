package com.fpt.service;

import com.fpt.repository.BlogRespository;
import com.fpt.repository.LaptopRepository;
import com.fpt.dto.SearchDto;
import com.fpt.model.Blog;
import com.fpt.model.Laptop;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    private final LaptopRepository laptopRepository;
    private final BlogRespository blogRespository;

    public SearchServiceImpl(LaptopRepository laptopRepository, BlogRespository blogRespository) {
        this.laptopRepository = laptopRepository;
        this.blogRespository = blogRespository;
    }

    @Override
    public List<SearchDto> searchLaptopByTitle(String keyword) {
        try {
            List<Laptop> list = laptopRepository.findByTitleContainingIgnoreCaseOrSummaryContainingIgnoreCaseOrMetaTitleContainingIgnoreCase(keyword, keyword, keyword);
            List<SearchDto> listS = new ArrayList<>();
            for (Laptop lt : list) {
                SearchDto sc = new SearchDto();
                sc.setType("laptop");
                sc.setTitle(lt.getTitle());
                sc.setSlug(lt.getSlug());
                sc.setImage(lt.getImage());
                listS.add(sc);
            }
            return listS;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public List<SearchDto> searchBogByTitle(String keyword) {
        try {
            List<Blog> list = blogRespository.findByNameContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword);
            List<SearchDto> listS = new ArrayList<>();
            for (Blog lt : list) {
                SearchDto sc = new SearchDto();
                sc.setType("blog");
                sc.setTitle(lt.getName());
                sc.setSlug(lt.getSlug());
                sc.setImage(lt.getImage());
                listS.add(sc);
            }
            return listS;
        } catch (Exception e) {

        }
        return null;
    }
}
