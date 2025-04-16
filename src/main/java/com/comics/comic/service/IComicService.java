package com.comics.comic.service;

import com.comics.comic.rest.dto.ComicRequest;
import com.comics.comic.rest.dto.ComicResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IComicService {

    ComicResponse getComic(String comic);

    Page<ComicResponse> getAllComics(Pageable pageable);

    ComicResponse addComic(ComicRequest comic);

    String deleteComic(String comicName);

}
