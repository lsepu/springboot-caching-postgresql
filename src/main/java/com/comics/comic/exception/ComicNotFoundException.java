package com.comics.comic.exception;
import java.util.UUID;

import static com.comics.comic.constants.ErrorMessageConstants.COMIC_ID_NOT_FOUND;
import static com.comics.comic.constants.ErrorMessageConstants.COMIC_NOT_FOUND;

public class ComicNotFoundException extends RuntimeException {
    public ComicNotFoundException(String comicName) {
        super(String.format(COMIC_NOT_FOUND, comicName));
    }
    public ComicNotFoundException(UUID id) {
        super(String.format(COMIC_ID_NOT_FOUND, id));
    }
}
