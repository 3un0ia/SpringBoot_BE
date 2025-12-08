package com.aivle._th_miniProject.book.dto;

import com.aivle._th_miniProject.book.entity.Category;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class BookCreateResponse {

    private Long bookId;
    private String title;
    private String author;
    private String description;
    private String coverImage;
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer stock;
    private BigDecimal price;
}
