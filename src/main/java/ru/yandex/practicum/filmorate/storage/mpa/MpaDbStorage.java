package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Component
public class MpaDbStorage implements MpaStorage{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<Mpa> getAll() {
        String sql = "select * from MPAS where MPA_ID = ?";

        return jdbcTemplate.query(sql, this::makeMpa);
    }

    public Mpa getById(int id) {
        String sql = "select * from MPAS where MPA_ID = ?";

        List<Mpa> mpas = jdbcTemplate.query(sql, this::makeMpa, id);
        if (mpas.isEmpty() || mpas.get(0) == null)
            return null;
        return mpas.get(0);
    }

    private Mpa makeMpa(ResultSet rs, int rowNum) throws SQLException {
        if (rowNum < 0)
            return null;
        int id = rs.getInt("MPA_ID");
        String name = rs.getString("MPA_NAME");
        return new Mpa(id, name);
    }
}
