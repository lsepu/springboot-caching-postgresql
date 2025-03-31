package com.comics.comic.service;


import com.comics.comic.entity.Comic;
import com.comics.comic.exception.ComicNotFoundException;
import com.comics.comic.repository.ComicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComicService implements IComicService {

    private final ComicRepository comicRepository;

    @Override
    public Comic getComic(String comicName){
        log.info("Getting comic " + comicName);
        Optional<Comic> comic = comicRepository.findByName(comicName);
        return comic.orElseThrow(() -> new ComicNotFoundException(comicName));
    }

    @Override
    public Page<Comic> getAllComics(Pageable pageable){
        log.info("Finding all comics");
        return comicRepository.findAll(pageable);
    }

    @Override
    public Comic addComic(Comic comic){
        log.info("Inserting comic " + comic.getName());
        Comic comicSaved = comicRepository.save(comic);
        return comicSaved;
    }

    @Override
    public String deleteComic(String comicName){
        log.info("Deleting comic " + comicName);

        Comic comic = comicRepository.findByName(comicName)
                .orElseThrow(() -> new ComicNotFoundException(comicName));

        comicRepository.deleteByName(comicName);
        return String.format("Comic %s was deleted", comicName);
    }


}
