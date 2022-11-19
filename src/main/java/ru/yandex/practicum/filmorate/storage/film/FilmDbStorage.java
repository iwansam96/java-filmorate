package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
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

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, MpaStorage mpaStorage, GenreStorage genreStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaStorage = mpaStorage;
        this.genreStorage = genreStorage;
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

        String sqlInsert = "insert into FILMS (FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION) " +
                "values (?, ?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[] {"FILM_ID"});

            String name = film.getName();
            String description = film.getDescription();
            LocalDate releaseDate = film.getReleaseDate();
            int duration = film.getDuration();

            ps.setString(1, name);
            ps.setString(2, description);
            ps.setDate(3, java.sql.Date.valueOf(releaseDate));
            ps.setInt(4, duration);

            return ps;
        }, keyHolder);

        int id = -1;
        Number keyId = keyHolder.getKey();
        if (keyId != null)
            id = keyId.intValue();

        return this.getById(id);
    }

    @Override
    public Film update(Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sqlInsert = "update FILMS set FILM_NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ? where FILM_ID = ?";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[] {"FILM_ID"});

            String name = film.getName();
            String description = film.getDescription();
            LocalDate releaseDate = film.getReleaseDate();
            int duration = film.getDuration();
            int updateFilmId = film.getId();

            ps.setString(1, name);
            ps.setString(2, description);
            ps.setDate(3, java.sql.Date.valueOf(releaseDate));
            ps.setInt(4, duration);
            ps.setInt(5, updateFilmId);

            return ps;
        }, keyHolder);

        int id = -1;
        Number keyId = keyHolder.getKey();
        if (keyId != null)
            id = keyId.intValue();

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

    private Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        if (rowNum < 0)
            return null;

        int id = rs.getInt("FILM_ID");
        String name = rs.getString("FILM_NAME");
        String description = rs.getString("DESCRIPTION");
        LocalDate releaseDate = rs.getDate("RELEASE_DATE").toLocalDate();
        int duration = rs.getInt("DURATION");
        Set<Integer> likes = getLikesByFilmId(id);
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

    private Set<Integer> getLikesByFilmId(int id) {
        String sql = "select * from LIKES where FILM_ID = ?";
        var likes = jdbcTemplate.query(sql, this::makeLike, id);
        if (likes.isEmpty() || likes.get(0) == null)
            return null;
        return new HashSet<>(likes);
    }

    private Integer makeLike(ResultSet rs, int rowNum) throws SQLException {
        if (rowNum < 0)
            return null;
        return rs.getInt("USER_ID");
    }

    private boolean isFilmWithIdPresent(int id) {
        return this.getById(id) != null;
    }

}
