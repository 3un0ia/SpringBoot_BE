package com.aivle._th_miniProject.Book.Entity;

import lombok.Getter;

@Getter
public enum Category {
    NOVEL("소설"),
    SCIENCE_FICTION("SF"),
    FANTASY("판타지"),
    MYSTERY("미스터리"),
    ROMANCE("로맨스"),
    SELF_HELP("자기계발"),
    ESSAY("에세이"),
    HISTORY("역사"),
    SCIENCE("과학"),
    OTHER("기타");

    private final String koreanName;

    Category(String koreanName) {
        this.koreanName = koreanName;
    }

}
