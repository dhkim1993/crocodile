package com.crocodile.api.lesson.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonDto {
    private Long id;
    private Long shopId;
    private String LessonName;
    private Integer lessonLimit;
    private LocalDate date;
}
