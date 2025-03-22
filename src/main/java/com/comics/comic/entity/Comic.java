package com.comics.comic.entity;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comic {

    private String name;
    private LocalDateTime datePublished;

}
