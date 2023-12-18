package com.fpt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDto {
    private Long id;
    private String description;
    private Long quantity;
    private Long amount;
    private Date createDate;
    private Date duration;
    private Boolean status;
}
