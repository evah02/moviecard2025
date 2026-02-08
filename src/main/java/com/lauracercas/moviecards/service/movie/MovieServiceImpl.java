package com.lauracercas.moviecards.service.movie;


import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.lauracercas.moviecards.dto.MovieForm;
import com.lauracercas.moviecards.model.Movie;

/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
@Service
public class MovieServiceImpl implements MovieService {

    private final RestTemplate template;
    private final String url;
    public MovieServiceImpl(RestTemplate template) {
        this.template = template;
        this.url = "https://moviecards-service-huertas.azurewebsites.net/movies";

    }

    @Override
    public List<Movie> getAllMovies() {
        Movie[] movies = template.getForObject(url, Movie[].class);
        return Arrays.asList(movies);
    }

    @Override
    public Movie save(Movie movie) {
        if (movie.getId() != null && movie.getId() > 0) { 
            template.put(url, movie); 
        } else { 
            movie.setId(0); 
            template.postForObject(url, movie, String.class); 
        } 
        return movie;
    }
  
    @Override
    public Movie saveFromForm(MovieForm form) {
        Movie movie = new Movie();
        movie.setId(form.getId());
        movie.setTitle(form.getTitle());
        movie.setReleaseYear(form.getReleaseYear());
        movie.setDuration(form.getDuration());
        movie.setCountry(form.getCountry());
        movie.setDirector(form.getDirector());
        movie.setGenre(form.getGenre());
        movie.setSinopsis(form.getSinopsis());

        return save(movie);
    }

    @Override
    public Movie getMovieById(Integer movieId) {
        return template.getForObject(url+"/"+movieId, Movie.class); 
    }
}
