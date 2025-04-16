package com.comics.comic.repository;

import com.comics.comic.entity.Comic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ComicRepository extends JpaRepository<Comic, UUID> {
    Optional<Comic> findByName(String name);
    void deleteByName(String name);

}
