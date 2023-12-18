package com.fpt.service;

import com.fpt.dto.FAQsDto;
import com.fpt.model.FAQs;

import java.util.List;

public interface FAQsService {

    FAQs findById(Long id);
    FAQsDto create(FAQsDto fapsdto);

    List<FAQsDto> listAllFaps();

    List<FAQsDto> listAllFaqsByLaptopId(Long id);

    boolean deleteFapsById(Long id);

    boolean createFapsMultiple(List<FAQsDto> listFaps, Long laptopId);

    FAQs updateFaq(Long id, FAQsDto faPs);
}
