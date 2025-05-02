package com.comics.comic.exception;
import java.util.UUID;

import static com.comics.comic.constants.ErrorMessageConstants.*;

public class ComicAlreadyExistsException extends RuntimeException {
    public ComicAlreadyExistsException(String comicName) {
        super(String.format(COMIC_ALREADY_EXISTS, comicName));
    }
}
