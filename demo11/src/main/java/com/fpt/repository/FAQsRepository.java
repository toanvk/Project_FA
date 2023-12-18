package com.fpt.repository;

import com.fpt.model.FAQs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FAQsRepository extends JpaRepository<FAQs,Long> {

    List<FAQs> findFAQsByLaptopId(Long laptopId);

}
