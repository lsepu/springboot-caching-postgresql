package com.comics.comic.rest;

import com.comics.comic.rest.dto.ComicRequest;
import com.comics.comic.rest.dto.ComicResponse;
import com.comics.comic.service.CacheInspectionService;
import com.comics.comic.service.ComicService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

import static com.comics.comic.constants.GeneralConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(COMIC_PATH)
public class ComicController {
    private final ComicService comicService;
    private final CacheInspectionService cacheInspectionService;

    @PostMapping
    @CacheEvict(value = ALL_COMICS_CACHE_NAME, allEntries = true)
    public ResponseEntity<ComicResponse> addComic(
            @RequestBody ComicRequest comic,
            UriComponentsBuilder uriBuilder) {
        ComicResponse comicAdded = comicService.addComic(comic);

        URI location = uriBuilder.path(COMIC_NAME_IN_PATH)
                .buildAndExpand(comicAdded.name())
                .toUri();

        return ResponseEntity.created(location).body(comicAdded);
    }

    @GetMapping
    @Cacheable(value = COMIC_CACHE_NAME, key = COMIC_NAME_CACHE_KEY)
    public ResponseEntity<ComicResponse> getComic(@RequestParam(name = "name") String comicName){
        ComicResponse comic = comicService.getComic(comicName);
        return ResponseEntity.ok(comic);
    }

    @GetMapping(ALL_COMICS_PATH)
    @Cacheable(ALL_COMICS_CACHE_NAME)
    public ResponseEntity<Page<ComicResponse>> getAllComics(@ParameterObject Pageable pageable){
        Page<ComicResponse> comics = comicService.getAllComics(pageable);

        if(comics.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(comics);
    }

    @DeleteMapping
    @Caching(evict = {
            @CacheEvict(value = COMIC_CACHE_NAME, key = COMIC_NAME_CACHE_KEY),
            @CacheEvict(value = ALL_COMICS_CACHE_NAME, allEntries = true)
    })
    public ResponseEntity<String> deleteComic(@RequestParam String comicName){
        String idOfComicEliminated = comicService.deleteComic(comicName);
        return ResponseEntity.ok(String.format("Comic %s with id %s was deleted", comicName, idOfComicEliminated));
    }

    @PutMapping
    @CachePut(value = COMIC_CACHE_NAME, key = COMIC_NAME_CACHE_KEY_IN_UPDATE)
    @CacheEvict(value = ALL_COMICS_CACHE_NAME, allEntries = true)
    public ResponseEntity<ComicResponse> updateComic(
                                                  @RequestBody ComicRequest comicRequest,
                                                  UriComponentsBuilder uriBuilder){
        ComicResponse comic = comicService.updateComic(comicRequest);
        return ResponseEntity.ok(comic);
    }

    /**
     * Added just to check cache values
     */
    @GetMapping(CACHE_PATH)
    public String getCache(@RequestParam(name = "name") String cacheName){
        return cacheInspectionService.printCacheContent(cacheName);
    }


}
