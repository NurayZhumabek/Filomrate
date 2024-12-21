package by.nuray.filomrate.controllers;


import by.nuray.filomrate.dao.FilmDao;
import by.nuray.filomrate.models.Film;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
public class FilmController {


    private final FilmDao filmDao;

    @Autowired
    public FilmController(FilmDao filmDao) {
        this.filmDao = filmDao;
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return filmDao.getAllFilms();

    }

    @PostMapping("/films")
    public ResponseEntity<?> createFilm(@RequestBody @Valid Film film, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.append(error.getField())
                        .append(": ")
                        .append(error.getDefaultMessage())
                        .append("\n");
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors.toString());
        }

        Film createdFilm = filmDao.addFilm(film);

        return new ResponseEntity<>(createdFilm, HttpStatus.CREATED);
    }

    @PutMapping("films")
    public ResponseEntity<?> updateFilm(@RequestBody @Valid Film film, BindingResult bindingResult) {

        if (film.getId() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        Optional<Film>  toBeUpdated = filmDao.getFilmById(film.getId());
        if (toBeUpdated.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        filmDao.updateFilm(film, film.getId());

        return ResponseEntity.ok(film);


    }
}
