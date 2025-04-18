package com.comics.comic.service;

import com.comics.comic.entity.Comic;
import com.comics.comic.exception.ComicNotFoundException;
import com.comics.comic.repository.ComicRepository;
import com.comics.comic.rest.dto.ComicRequest;
import com.comics.comic.rest.dto.ComicResponse;
import com.comics.comic.util.mapper.ComicMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComicService implements IComicService {
    private final ComicRepository comicRepository;
    private final ComicMapper comicMapper;

    @Override
    public ComicResponse getComic(String comicName){
        log.info("Getting comic " + comicName);
        Comic comic = comicRepository.findByName(comicName)
                .orElseThrow(() -> new ComicNotFoundException(comicName));
        return comicMapper.toComicResponse(comic);
    }

    public ComicResponse getComic(UUID id){
        log.info("Getting comic " + id);
        Comic comic = comicRepository.findById(id)
                .orElseThrow(() -> new ComicNotFoundException(id));
        return comicMapper.toComicResponse(comic);
    }


    @Override
    public Page<ComicResponse> getAllComics(Pageable pageable){
        log.info("Finding all comics");
        return comicRepository.findAll(pageable)
                .map(comicMapper::toComicResponse);
    }

    @Override
    public ComicResponse addComic(ComicRequest comicRequest){
        log.info("Inserting comic " + comicRequest.name());
        Comic comic = comicMapper.toComic(comicRequest);
        Comic comicSaved = comicRepository.save(comic);
        return comicMapper.toComicResponse(comicSaved);
    }

    @Override
    @Transactional
    public String deleteComic(String comicName){
        log.info("Deleting comic " + comicName);

        Comic comic = comicRepository.findByName(comicName)
                .orElseThrow(() -> new ComicNotFoundException(comicName));

        comicRepository.deleteByName(comic.getName());
        return String.format("Comic %s was deleted", comic.getName());
    }


}
