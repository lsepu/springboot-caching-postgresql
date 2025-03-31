package com.comics.comic.repository;

import com.comics.comic.entity.Comic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComicRepository extends JpaRepository<Comic, Long> {
    Optional<Comic> findByName(String name);
    void deleteByName(String name);

}
