package com.comics.comic.rest;


import com.comics.comic.entity.Comic;
import com.comics.comic.rest.dto.ComicResponse;
import com.comics.comic.service.ComicService;
import com.comics.comic.util.mapper.ComicMapper;
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
    private final ComicMapper comicMapper;

    @PostMapping
    public ResponseEntity<ComicResponse> addComic(
            @RequestBody Comic comic,
            UriComponentsBuilder uriBuilder) {
        Comic comicAdded = comicService.addComic(comic);

        URI location = uriBuilder.path(COMIC_NAME_IN_PATH)
                .buildAndExpand(comicAdded.getName())
                .toUri();

        return ResponseEntity.created(location).body(comicMapper.toComicResponse(comicAdded));
    }

    @GetMapping
    public ResponseEntity<ComicResponse> getComic(@RequestParam(name = "name") String comicName){
        Comic comic = comicService.getComic(comicName);
        return ResponseEntity.ok(comicMapper.toComicResponse(comic));
    }

    @GetMapping
    public ResponseEntity<Page<ComicResponse>> getAllComics(@ParameterObject Pageable pageable){

        Page<ComicResponse> comics = comicService.getAllComics(pageable)
                .map(comicMapper::toComicResponse);

        if(comics.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(comics);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteComic(@RequestParam String comicName){
        String comicResponse = comicService.deleteComic(comicName);
        return ResponseEntity.ok(comicResponse);
    }



}
