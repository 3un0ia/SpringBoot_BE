package com.aivle._th_miniProject.Book.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CoverUpdateResponse {
    private Long bookId;
    private String coverImage;
    private LocalDateTime updatedAt;
}