package com.fpt.controller;

import com.fpt.dto.MetadataGroupDto;
import com.fpt.exceptions.ApiExceptionResponse;
import com.fpt.model.MetadataGroup;
import com.fpt.service.MetadataGroupService;
import com.fpt.utils.ApiSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/metadata-group")
public class MetadataGroupController {
    private final MetadataGroupService metadataGroupService;

    public MetadataGroupController(MetadataGroupService metadataGroupService) {
        this.metadataGroupService = metadataGroupService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createMetaGroup(@RequestBody MetadataGroup metadataGroup) {
        MetadataGroup m = metadataGroupService.createMetadataGroup(metadataGroup);
        if (m != null) {
            final ApiSuccessResponse response = new ApiSuccessResponse("Successful", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> updateMetaGroup(@PathVariable Long id, @RequestBody MetadataGroup metadataGroup) {
        MetadataGroup m = metadataGroupService.updateMetaById(id, metadataGroup);
        if (m != null) {
            final ApiSuccessResponse response = new ApiSuccessResponse("Update Successful", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<MetadataGroup>> getAll() {
        final List<MetadataGroup> listMetaGroup = metadataGroupService.listAll();
        List<MetadataGroup> metadataGroups = new ArrayList<>();
        for (MetadataGroup metadataGroup : listMetaGroup) {
            MetadataGroup m = new MetadataGroup();
            m.setId(metadataGroup.getId());
            m.setName(metadataGroup.getName());
            metadataGroups.add(m);
        }
        return ResponseEntity.ok().body(metadataGroups);
    }

    @GetMapping("/get/{laptopSlug}")
    public ResponseEntity<?> getMetadataGroupByLaptopId(@PathVariable String laptopSlug) {
        List<MetadataGroupDto> metadataGroupList = metadataGroupService.findByLaptopSlug(laptopSlug);
        if (metadataGroupList != null) {
            return ResponseEntity.status(HttpStatus.OK).body(metadataGroupList);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.NO_CONTENT, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
