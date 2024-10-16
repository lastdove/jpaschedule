package com.sparta.scheduleappjpa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {

    @NotBlank
    private String content;
}
