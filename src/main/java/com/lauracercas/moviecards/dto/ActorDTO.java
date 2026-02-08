package com.lauracercas.moviecards.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class ActorDTO {
    private Integer id;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deathDate;
    private String country;
}
