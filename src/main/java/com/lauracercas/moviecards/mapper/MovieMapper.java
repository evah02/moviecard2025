package com.lauracercas.moviecards.mapper;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.lauracercas.moviecards.dto.MovieDTO;
import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.model.Movie;

public class MovieMapper {
    private MovieMapper() {
    }

    public static MovieDTO toDto(Movie movie) {
        if (movie == null) return null;

        MovieDTO dto = new MovieDTO();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setReleaseYear(movie.getReleaseYear());
        dto.setDuration(movie.getDuration());
        dto.setCountry(movie.getCountry());
        dto.setDirector(movie.getDirector());
        dto.setGenre(movie.getGenre());
        dto.setSinopsis(movie.getSinopsis());

        if (movie.getActors() != null) {
            dto.setActors(
                movie.getActors().stream()
                    .map(ActorMapper::toDto)
                    .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public static Movie toEntity(MovieDTO dto, Movie movie) {
        if (dto == null) return null;

        movie.setTitle(dto.getTitle());
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setDuration(dto.getDuration());
        movie.setCountry(dto.getCountry());
        movie.setDirector(dto.getDirector());
        movie.setGenre(dto.getGenre());
        movie.setSinopsis(dto.getSinopsis());

        if (dto.getActors() != null) {
            List<Actor> actors = dto.getActors().stream()
                .map(aDto -> {
                    Actor a = ActorMapper.toEntity(aDto, new Actor());
                    a.setId(aDto.getId());
                    return a;
                })
                .collect(Collectors.toList());

            movie.setActors(actors);
        } else {
            movie.setActors(new ArrayList<>());
        }
        
        return movie;
    }
}
