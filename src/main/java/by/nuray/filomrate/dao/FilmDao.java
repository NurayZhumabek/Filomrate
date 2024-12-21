package by.nuray.filomrate.dao;

import by.nuray.filomrate.models.Film;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class FilmDao {
    private static int COUNTER ;

    private List<Film> filmList;


    {
        filmList = new ArrayList<>();
        filmList.add(new Film(++COUNTER,"First film","My fist film", LocalDate.of(2024,12,21),90));

    }

    public List<Film> getAllFilms() {
        return filmList;
    }

    public Optional<Film> getFilmById(int id) {
        return filmList.stream().filter(f -> f.getId() == id).findAny();

    }

    public Film addFilm(Film film) {
        film.setId(++COUNTER);
        filmList.add(film);
        return film;
    }
    public void updateFilm(Film film,int id) {
        Optional<Film> f = getFilmById(id);
        f.get().setName(film.getName());
        f.get().setDescription(film.getDescription());
        f.get().setReleaseDate(film.getReleaseDate());
        f.get().setDuration(film.getDuration());

    }







}
