package com.comics.comic.rest;

import com.comics.comic.rest.dto.ComicRequest;
import com.comics.comic.rest.dto.ComicResponse;
import com.comics.comic.service.CacheInspectionService;
import com.comics.comic.service.ComicService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
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
    public ResponseEntity<ComicResponse> getComic(@RequestParam(name = "name") String comicName){
        ComicResponse comic = comicService.getComic(comicName);
        return ResponseEntity.ok(comic);
    }

    @GetMapping(ALL_COMICS_PATH)
    public ResponseEntity<Page<ComicResponse>> getAllComics(@ParameterObject Pageable pageable){
        Page<ComicResponse> comics = comicService.getAllComics(pageable);

        if(comics.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(comics);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteComic(@RequestParam String comicName){
        String idOfComicEliminated = comicService.deleteComic(comicName);
        return ResponseEntity.ok(String.format("Comic %s with id %s was deleted", comicName, idOfComicEliminated));
    }

    @PutMapping
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
