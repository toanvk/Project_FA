package com.fpt.repository;

import com.fpt.model.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetadataRepository extends JpaRepository<Metadata,Long> {

    List<Metadata> findMetadataByLaptop_Id(Long laptopId);
}
