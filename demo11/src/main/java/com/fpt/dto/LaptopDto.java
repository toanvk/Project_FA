package com.fpt.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LaptopDto {
    private Long id;
    private String userName;
    private String title;
    private String metaTitle;
    private String slug;
    private String summary;
    private String image;
    private String sku;
    private Float discount;
    private Float price;
    private Set<DiscountDto> discountDtoSet;
    private int quantity;
    private boolean status;
    private Long categoryId;
    private Long brandId;
    private Set<MetadataDto> metadataDtoSet;
}

