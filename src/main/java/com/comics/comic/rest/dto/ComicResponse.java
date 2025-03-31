package com.comics.comic.rest.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public record ComicResponse(
        UUID id,
        String name,
        LocalDate datePublished
) implements Serializable {
}
