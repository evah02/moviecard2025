package com.lauracercas.moviecards.mapper;

import com.lauracercas.moviecards.dto.ActorDTO;
import com.lauracercas.moviecards.model.Actor;

public class ActorMapper {
     private ActorMapper() {
    }

    public static ActorDTO toDto(Actor actor) {
        if (actor == null) return null;

        ActorDTO dto = new ActorDTO();
        dto.setId(actor.getId());
        dto.setName(actor.getName());
        dto.setBirthDate(actor.getBirthDate());
        dto.setDeathDate(actor.getDeathDate());
        dto.setCountry(actor.getCountry());
        return dto;
    }

    public static Actor toEntity(ActorDTO dto, Actor actor) {
        if (dto == null) return null;

        actor.setName(dto.getName());
        actor.setBirthDate(dto.getBirthDate());
        actor.setDeathDate(dto.getDeathDate());
        actor.setCountry(dto.getCountry());
        return actor;
    }
}
