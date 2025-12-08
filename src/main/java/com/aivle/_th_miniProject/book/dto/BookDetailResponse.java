package com.aivle._th_miniProject.book.dto;

import com.aivle._th_miniProject.book.entity.Book;
import com.aivle._th_miniProject.book.entity.Category;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BookDetailResponse {

    private Long bookId;
    private String title;
    private String author;
    private String description;
    private String coverImage;
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static BookDetailResponse from(Book book) {
        return BookDetailResponse.builder()
                .bookId(book.getBookId())
                .title(book.getTitle())
                .author(book.getUser().getName())
                .description(book.getDescription())
                .coverImage(book.getCoverImage())
                .category(book.getCategory())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
    }
}