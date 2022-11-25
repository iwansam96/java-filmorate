package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Component
@Primary
public class FilmDbStorage implements FilmStorage{

    private final JdbcTemplate jdbcTemplate;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;
    private final LikeStorage likeStorage;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, MpaStorage mpaStorage, GenreStorage genreStorage,
                         LikeStorage likeStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaStorage = mpaStorage;
        this.genreStorage = genreStorage;
        this.likeStorage = likeStorage;
    }

    @Override
    public Collection<Film> getAll() {
        String sql = "select * from FILMS";

        return jdbcTemplate.query(sql, this::makeFilm);
    }

    @Override
    public Film getById(int id) {
        String sql = "select * from FILMS where FILM_ID = ?";

        List<Film> films = jdbcTemplate.query(sql, this::makeFilm, id);
        if (!films.isEmpty() && films.get(0) != null) {
            return films.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Film create(Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "insert into FILMS (FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA) " +
                "values (?, ?, ?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"FILM_ID"});

            String name = film.getName();
            String description = film.getDescription();
            LocalDate releaseDate = film.getReleaseDate();
            int duration = film.getDuration();
            int mpa = film.getMpa().getId();

            ps.setString(1, name);
            ps.setString(2, description);
            ps.setDate(3, java.sql.Date.valueOf(releaseDate));
            ps.setInt(4, duration);
            ps.setInt(5, mpa);

            return ps;
        }, keyHolder);

        int id = -1;
        Number keyId = keyHolder.getKey();
        if (keyId != null)
            id = keyId.intValue();

        var genres = film.getGenres();
        if (genres != null) {
            for (Genre genre : genres)
                this.addGenreToFilm(id, genre.getId());
        }

        return this.getById(id);
    }

    @Override
    public Film update(Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "update FILMS set FILM_NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, MPA = ? " +
                "where FILM_ID = ?";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"FILM_ID"});

            String name = film.getName();
            String description = film.getDescription();
            LocalDate releaseDate = film.getReleaseDate();
            int duration = film.getDuration();
            int mpa = film.getMpa().getId();
            int updateFilmId = film.getId();

            ps.setString(1, name);
            ps.setString(2, description);
            ps.setDate(3, java.sql.Date.valueOf(releaseDate));
            ps.setInt(4, duration);
            ps.setInt(5, mpa);
            ps.setInt(6, updateFilmId);

            return ps;
        }, keyHolder);

        int id = -1;
        Number keyId = keyHolder.getKey();
        if (keyId != null)
            id = keyId.intValue();

        var genres = film.getGenres();
        updateFilmGenres(id, genres);

        return this.getById(id);
    }

    @Override
    public Film delete(Film film) {
        if (isFilmWithIdPresent(film.getId())) {
            String sql = "delete from FILMS where FILM_ID = ?";

            jdbcTemplate.update(sql, film.getId());
            return film;
        }
        return null;
    }

    @Override
    public Collection<Film> getTopLiked(int count) {
        String sql = "select L.FILM_ID, FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA from LIKES as L " +
                "left join FILMS as F on F.FILM_ID = L.FILM_ID limit ?";

        Collection<Film> result = jdbcTemplate.query(sql, this::makeFilm, count);
        if (result.isEmpty()) {
            String sqlGetAll = "select * from FILMS limit ?";
            result = jdbcTemplate.query(sqlGetAll, this::makeFilm, count);
        }
        return result;
    }

    private Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        if (rowNum < 0)
            return null;

        int id = rs.getInt("FILM_ID");
        String name = rs.getString("FILM_NAME");
        String description = rs.getString("DESCRIPTION");
        LocalDate releaseDate = rs.getDate("RELEASE_DATE").toLocalDate();
        int duration = rs.getInt("DURATION");
        Set<Integer> likes = likeStorage.getLikesByFilmId(id);
//        mpa;
        int mpaId = rs.getInt("MPA");
        Mpa mpa = mpaStorage.getById(mpaId);
//        genre;
        var genres = genreStorage.getGenresByFilmId(id);

        Film film = new Film(id, name, description, releaseDate, duration, likes);
        film.setMpa(mpa);
        film.setGenres(genres);

        return film;
    }

    private boolean isFilmWithIdPresent(int id) {
        return this.getById(id) != null;
    }

    // Genres
    private void addGenreToFilm(int filmId, int genreId) {
        String sql = "merge into FILMS_GENRES (FILM_ID, GENRE_ID) values ( ?, ? )";

        jdbcTemplate.update(sql, filmId, genreId);
    }

    private void removeAllGenresForFilm(int filmId) {
        String sql = "delete from FILMS_GENRES where FILM_ID = ?";
        jdbcTemplate.update(sql, filmId);
    }

    private void updateFilmGenres(int filmId, Collection<Genre> genres) {
        if (genres == null || genres.isEmpty())
            removeAllGenresForFilm(filmId);
        else {
            removeAllGenresForFilm(filmId);
            for (Genre genre : genres)
                addGenreToFilm(filmId, genre.getId());
        }
    }
}
