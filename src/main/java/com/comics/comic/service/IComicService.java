package com.comics.comic.service;

import com.comics.comic.entity.Comic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IComicService {

    Comic getComic(String comic);

    Page<Comic> getAllComics(Pageable pageable);

    Comic addComic(Comic comic);

    String deleteComic(String comicName);

}
