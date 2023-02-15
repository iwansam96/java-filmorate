package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<Genre> getAll() {
        String sql = "select * from GENRES";

        return jdbcTemplate.query(sql, this::makeGenre);
    }
    public Genre getById(int id) {
        String sql = "select * from GENRES where GENRE_ID = ?";

        List<Genre> genres = jdbcTemplate.query(sql, this::makeGenre, id);
        if (genres.isEmpty() || genres.get(0) == null)
            return null;
        return genres.get(0);
    }

    public Collection<Genre> getGenresByFilmId(int filmId) {
        Collection<Genre> genres = new ArrayList<>();
        String sql = "select GENRE_ID from FILMS_GENRES where FILM_ID = ?";

        List<Integer> genreIds = jdbcTemplate.queryForList(sql, Integer.class, filmId);

        if (!genreIds.isEmpty() && genreIds.get(0) != null) {
            for (int genreId : genreIds) {
                genres.add(this.getById(genreId));
            }
        }

        return genres;
    }

    private Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        if (rowNum < 0)
            return null;
        int id = rs.getInt("GENRE_ID");
        String name = rs.getString("GENRE_NAME");
        return new Genre(id, name);
    }
}
