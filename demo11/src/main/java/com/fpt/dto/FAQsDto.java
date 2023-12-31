package com.fpt.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FAQsDto {
    private Long id;
    private String title;
    private String content;
    private Long laptop_id;
}
