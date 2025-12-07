package com.aivle._th_miniProject.Book.DTO;

import com.aivle._th_miniProject.Book.Entity.Category;
import lombok.Builder;
import lombok.Getter;
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
    private LocalDateTime updateAt;
}
