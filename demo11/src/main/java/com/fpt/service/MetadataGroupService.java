package com.fpt.service;

import com.fpt.dto.MetadataGroupDto;
import com.fpt.model.MetadataGroup;

import java.util.List;

public interface MetadataGroupService {
    MetadataGroup createMetadataGroup(MetadataGroup metadataGroup);

    MetadataGroup updateMetaById(Long id, MetadataGroup metadataGroup);

    List<MetadataGroup> listAll();

    List<MetadataGroupDto> findByLaptopSlug(String slug);
}
