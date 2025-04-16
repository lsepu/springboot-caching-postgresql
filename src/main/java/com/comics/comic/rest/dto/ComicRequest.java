package com.comics.comic.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.time.LocalDate;

public record ComicRequest(
        @Schema(example="Batman")  @NotBlank String name,
        @Schema(example = "2025-03-31") @NotBlank LocalDate datePublished
) implements Serializable {
}
