package com.comics.comic.rest;

import com.comics.comic.rest.dto.ComicRequest;
import com.comics.comic.rest.dto.ComicResponse;
import com.comics.comic.service.ComicService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.UUID;

import static com.comics.comic.constants.GeneralConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(COMIC_PATH)
public class ComicController {
    private final ComicService comicService;

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

    @GetMapping("/all")
    public ResponseEntity<Page<ComicResponse>> getAllComics(@ParameterObject Pageable pageable){
        Page<ComicResponse> comics = comicService.getAllComics(pageable);

        if(comics.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(comics);
    }

    @GetMapping(ID_IN_PATH)
    public ResponseEntity<ComicResponse> getComic(@PathVariable UUID id){
        ComicResponse comic = comicService.getComic(id);
        return ResponseEntity.ok(comic);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteComic(@RequestParam String comicName){
        String comicResponse = comicService.deleteComic(comicName);
        return ResponseEntity.ok(comicResponse);
    }



}
