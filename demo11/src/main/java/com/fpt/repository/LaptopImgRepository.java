package com.fpt.repository;

import com.fpt.model.LaptopImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaptopImgRepository extends JpaRepository<LaptopImg,Long> {
    List<LaptopImg> findAllByLaptop_Id(Long id);
    void deleteAllByLaptopId(Long id);
}
