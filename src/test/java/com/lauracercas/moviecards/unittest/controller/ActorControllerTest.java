package com.lauracercas.moviecards.unittest.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.lauracercas.moviecards.controller.ActorController;
import com.lauracercas.moviecards.dto.ActorForm;
import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.model.Movie;
import com.lauracercas.moviecards.service.actor.ActorService;
import com.lauracercas.moviecards.util.Messages;

/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
class ActorControllerTest {

    private ActorController controller;

    @Mock
    private ActorService actorServiceMock;

    private AutoCloseable closeable;
    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        closeable = openMocks(this);
        controller = new ActorController(actorServiceMock);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }


    @Test
    void shouldGoListActorAndGetAllActors() {
        List<Actor> actors = new ArrayList<>();

        when(actorServiceMock.getAllActors()).thenReturn(actors);

        String viewName = controller.getActorsList(model);

        assertEquals("actors/list", viewName);
    }

    @Test
    void shouldInitializeActor() {
        String viewName = controller.newActor(model);

        assertEquals("actors/form", viewName);

        verify(model).addAttribute("actor", new Actor());
        verify(model).addAttribute("title", Messages.NEW_ACTOR_TITLE);
    }

    @Test
    void shouldSaveActorWithNoErrors() {
        Actor actor = new Actor();
        ActorForm form = new ActorForm();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);

        when(actorServiceMock.saveFromForm(any(ActorForm.class))).thenReturn(actor);

        String viewName = controller.saveActor(form, result, model);

        assertEquals("actors/form", viewName);

        verify(actorServiceMock).saveFromForm(form);
        verify(model).addAttribute("actor", actor);
        verify(model).addAttribute("title", Messages.EDIT_ACTOR_TITLE);
        verify(model).addAttribute("message", Messages.SAVED_ACTOR_SUCCESS);
    }

    @Test
    void shouldUpdateActorWithNoErrors() {
        ActorForm form = new ActorForm();
        form.setId(1);
        Actor actor = new Actor();
        actor.setId(1);
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);

        when(actorServiceMock.saveFromForm(any(ActorForm.class))).thenReturn(actor);

        String viewName = controller.saveActor(form, result, model);

        assertEquals("actors/form", viewName);

        verify(actorServiceMock).saveFromForm(form);
        verify(model).addAttribute("actor", actor);
        verify(model).addAttribute("title", Messages.EDIT_ACTOR_TITLE);
        verify(model).addAttribute("message", Messages.UPDATED_ACTOR_SUCCESS);
    }

    @Test
    void shouldTrySaveActorWithErrors() {
        ActorForm form = new ActorForm();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        String viewName = controller.saveActor(form, result, model);

        assertEquals("actors/form", viewName);

        verifyNoInteractions(model);
    }

    @Test
    void shouldGoToEditActor() {
        Actor actor = new Actor();
        actor.setId(1);
        List<Movie> movies = List.of(new Movie());
        actor.setMovies(movies);
        when(actorServiceMock.getActorById(actor.getId())).thenReturn(actor);

        String viewName = controller.editActor(actor.getId(), model);

        assertEquals("actors/form", viewName);

        verify(model).addAttribute("actor", actor);
        verify(model).addAttribute("movies", movies);
        verify(model).addAttribute("title", Messages.EDIT_ACTOR_TITLE);
    }


}