package ru.yandex.practicum.filmorate.storage.like;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Set;

public interface LikeStorage {

    Integer addLike(int filmId, int userId);

    Integer deleteLike(int filmId, int userId);

    Set<Integer> getLikesByFilmId(int id);
}
