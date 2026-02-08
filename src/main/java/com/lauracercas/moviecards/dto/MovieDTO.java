package com.lauracercas.moviecards.dto;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDTO {
    private Integer id;
    private String title;
    private Integer releaseYear;
    private Integer duration;
    private String country;
    private String director;
    private String genre;
    private String sinopsis;

    private List<ActorDTO> actors;
}
