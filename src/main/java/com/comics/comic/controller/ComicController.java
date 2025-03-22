package com.comics.comic.controller;

import com.comics.comic.entity.Comic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comic")
public class ComicController {

    @PostMapping
    public ResponseEntity<Comic> addComic(@RequestBody Comic comic) {
        return null;
    }

    @GetMapping
    public ResponseEntity<String> getComic(@RequestParam String comic){
        return null;
    }

    @GetMapping("/all")
    public List<Comic> getAllComics(){
        return null;
    }



}
