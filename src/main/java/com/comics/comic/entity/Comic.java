package com.comics.comic.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comics")
public class Comic {

    @Id
    @GeneratedValue
    private Long Id;

    private String name;
    private LocalDate datePublished;

}
