package com.lauracercas.moviecards.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.lauracercas.moviecards.dto.ActorDTO;
import com.lauracercas.moviecards.mapper.ActorMapper;
import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.model.Movie;
import com.lauracercas.moviecards.service.actor.ActorService;
import com.lauracercas.moviecards.util.Messages;


/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
@Controller
public class ActorController {
    private static final String ACTOR_FORM_VIEW = "actors/form";
    private static final String ACTOR_ATTRIBUTE = "actor";
    private static final String TITLE_ATTRIBUTE = "title";
    
    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("actors")
    public String getActorsList(Model model) {
        model.addAttribute("actors", actorService.getAllActors());
        return "actors/list";
    }

    @GetMapping("actors/new")
    public String newActor(Model model) {
        model.addAttribute(ACTOR_ATTRIBUTE, new ActorDTO());
        model.addAttribute(TITLE_ATTRIBUTE, Messages.NEW_ACTOR_TITLE);
        return ACTOR_FORM_VIEW;
    }

    @PostMapping("saveActor")
    public String saveActor(@ModelAttribute ActorDTO actorDto, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            Actor actorToSave = ActorMapper.toEntity(actorDto, new Actor());
            Actor actorSaved = actorService.save(actorToSave);
            if (actorDto.getId() != null) {
                model.addAttribute("message", Messages.UPDATED_ACTOR_SUCCESS);
            } else {
                model.addAttribute("message", Messages.SAVED_ACTOR_SUCCESS);
            }

            model.addAttribute(ACTOR_ATTRIBUTE, ActorMapper.toDto(actorSaved));
            model.addAttribute(TITLE_ATTRIBUTE, Messages.EDIT_ACTOR_TITLE);
        }
        return ACTOR_FORM_VIEW;
    }

    @GetMapping("editActor/{actorId}")
    public String editActor(@PathVariable Integer actorId, Model model) {
        Actor actor = actorService.getActorById(actorId);
        List<Movie> movies = actor.getMovies();
        model.addAttribute(ACTOR_ATTRIBUTE, ActorMapper.toDto(actor));
        model.addAttribute("movies", movies);

        model.addAttribute(TITLE_ATTRIBUTE, Messages.EDIT_ACTOR_TITLE);

        return ACTOR_FORM_VIEW;
    }


}
