package ru.yandex.practicum.filmorate.util;

public class IdGenerator {
    private static int filmId = 1;
    private static int userId = 1;

    public static int generateId(String type) {
        if ("film".equals(type))
            return filmId++;
        else if ("user".equals(type)) {
            return userId++;
        }
        else
            return -1;
    }
}
