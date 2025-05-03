package com.comics.comic.service;

import com.comics.comic.entity.Comic;
import com.comics.comic.exception.ComicAlreadyExistsException;
import com.comics.comic.exception.ComicNotFoundException;
import com.comics.comic.repository.ComicRepository;
import com.comics.comic.rest.dto.ComicRequest;
import com.comics.comic.rest.dto.ComicResponse;
import com.comics.comic.util.mapper.ComicMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.comics.comic.constants.GeneralConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComicService implements IComicService {
    private final ComicRepository comicRepository;
    private final ComicMapper comicMapper;

    @Override
    @Cacheable(value = COMIC_CACHE_NAME, key = COMIC_NAME_CACHE_KEY)
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
    @Cacheable(ALL_COMICS_CACHE_NAME)
    public Page<ComicResponse> getAllComics(Pageable pageable){
        log.info("Finding all comics");
        return comicRepository.findAll(pageable)
                .map(comicMapper::toComicResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = ALL_COMICS_CACHE_NAME, allEntries = true)
    public ComicResponse addComic(ComicRequest comicRequest){

        comicRepository.findByName(comicRequest.name())
                .ifPresent(comic -> {
                    throw new ComicAlreadyExistsException(comicRequest.name());
                });

        log.info("Inserting comic " + comicRequest.name());
        Comic comic = comicMapper.toComic(comicRequest);
        Comic comicSaved = comicRepository.save(comic);
        return comicMapper.toComicResponse(comicSaved);
    }

    @Override
    @Transactional
    @CachePut(value = COMIC_CACHE_NAME, key = COMIC_NAME_CACHE_KEY_IN_UPDATE)
    @CacheEvict(value = ALL_COMICS_CACHE_NAME, allEntries = true)
    public ComicResponse updateComic(ComicRequest comicRequest){
        Comic comic = comicRepository.findByName(comicRequest.name())
                .orElseThrow(() -> new ComicNotFoundException(comicRequest.name()));

        comic.setDatePublished(comicRequest.datePublished());
        comic.setName(comicRequest.name());

        Comic comicSaved = comicRepository.save(comic);
        return comicMapper.toComicResponse(comicSaved);
    }



    @Override
    @Caching(evict = {
            @CacheEvict(value = COMIC_CACHE_NAME, key = COMIC_NAME_CACHE_KEY),
            @CacheEvict(value = ALL_COMICS_CACHE_NAME, allEntries = true)
    })
    @Transactional
    public String deleteComic(String comicName){
        log.info("Deleting comic " + comicName);

        Comic comic = comicRepository.findByName(comicName)
                .orElseThrow(() -> new ComicNotFoundException(comicName));

        comicRepository.deleteByName(comic.getName());
        return comic.getId().toString();
    }


}
