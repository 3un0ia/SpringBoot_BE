package com.aivle._th_miniProject.book.dto;

import com.aivle._th_miniProject.book.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookCreateRequest {

    @NotBlank(message = "제목은 필수 입력입니다.")
    private String title;

    @NotBlank(message = "설명은 필수 입력입니다.")
    private String description;

    private String coverImage;

    @NotNull(message = "카테고리는 필수 입력입니다.")
    private Category category; // enum
}
