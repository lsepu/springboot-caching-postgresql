package com.comics.comic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import static com.comics.comic.constants.ErrorMessageConstants.COMIC_NOT_FOUND;

public class ComicNotFoundException extends ResponseStatusException {
    public ComicNotFoundException(String comicName) {
        super(HttpStatus.NOT_FOUND, String.format(COMIC_NOT_FOUND, comicName));
    }
}
