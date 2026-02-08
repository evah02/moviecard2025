package com.lauracercas.moviecards.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.lauracercas.moviecards.dto.MovieForm;
import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.model.Movie;
import com.lauracercas.moviecards.service.movie.MovieService;
import com.lauracercas.moviecards.util.Messages;


/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
@Controller
public class MovieController {
    private static final String MOVIES_FORM_VIEW = "movies/form";
    private static final String MOVIES_ATTRIBUTE = "movie";
    private static final String TITLE_ATTRIBUTE = "title";
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("movies")
    public String getMoviesList(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "movies/list";
    }

    @GetMapping("movies/new")
    public String newMovie(Model model) {
        model.addAttribute(MOVIES_ATTRIBUTE, new MovieForm());
        model.addAttribute(TITLE_ATTRIBUTE, Messages.NEW_MOVIE_TITLE);
        return MOVIES_FORM_VIEW;
    }

    @PostMapping("saveMovie")
    public String saveMovie(@ModelAttribute("movie") MovieForm form, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            Movie movieSaved = movieService.saveFromForm(form);
            if (movieSaved.getId() != null) {
                model.addAttribute("message", Messages.UPDATED_MOVIE_SUCCESS);
            } else {
                model.addAttribute("message", Messages.SAVED_MOVIE_SUCCESS);
            }

            model.addAttribute(MOVIES_ATTRIBUTE, movieSaved);
            model.addAttribute(TITLE_ATTRIBUTE, Messages.EDIT_MOVIE_TITLE);
        }
    return MOVIES_FORM_VIEW;
    }

    @GetMapping("editMovie/{movieId}")
    public String editMovie(@PathVariable Integer movieId, Model model) {
        Movie movie = movieService.getMovieById(movieId);
        MovieForm form = new MovieForm();
        form.setId(movie.getId());
        form.setTitle(movie.getTitle());
        form.setReleaseYear(movie.getReleaseYear());
        form.setDuration(movie.getDuration());
        form.setCountry(movie.getCountry());
        form.setDirector(movie.getDirector());
        form.setGenre(movie.getGenre());
        form.setSinopsis(movie.getSinopsis());
        List<Actor> actors = movie.getActors();
        model.addAttribute(MOVIES_ATTRIBUTE, form);
        model.addAttribute("actors", actors);

        model.addAttribute(TITLE_ATTRIBUTE, Messages.EDIT_MOVIE_TITLE);

        return MOVIES_FORM_VIEW;
    }


}
