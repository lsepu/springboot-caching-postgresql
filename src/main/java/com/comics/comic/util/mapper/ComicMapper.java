package com.comics.comic.util.mapper;


import com.comics.comic.entity.Comic;
import com.comics.comic.rest.dto.ComicResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComicMapper {

    @Mapping(target = "id", ignore = true)
    ComicResponse toComicResponse(Comic comic);

}
