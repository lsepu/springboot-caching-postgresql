package com.comics.comic.rest.dto;

import java.io.Serializable;
import java.time.LocalDate;

public record ComicRequest(
        String name,
        LocalDate datePublished
) implements Serializable {
}
