package com.lauracercas.moviecards.service.actor;


import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lauracercas.moviecards.dto.ActorForm;
import com.lauracercas.moviecards.model.Actor;

/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
@Service
public class ActorServiceImpl implements ActorService {

    private final RestTemplate template;
    private final String url;
    
    public ActorServiceImpl(RestTemplate template) {
        this.template = template;
        this.url = "https://moviecards-service-huertas.azurewebsites.net/actors";
    }

    @Override
    public List<Actor> getAllActors() {
        Actor[] actores = template.getForObject(url, Actor[].class); 
        return Arrays.asList(actores);
    }

    @Override
    public Actor save(Actor actor) {
        if (actor.getId() != null && actor.getId() > 0) { 
            template.put(url, actor); 
        } else { 
            actor.setId(0); 
            template.postForObject(url, actor, String.class); 
        }
        return actor;
    }

    @Override
    public Actor saveFromForm(ActorForm form) {
        Actor actor = new Actor();
        actor.setId(form.getId());
        actor.setName(form.getName());
        actor.setBirthDate(form.getBirthDate());
        actor.setDeathDate(form.getDeathDate());
        actor.setCountry(form.getCountry());

        return save(actor);
    }


    @Override
    public Actor getActorById(Integer actorId) {
        return template.getForObject(url+"/"+actorId, Actor.class);
    }
}
