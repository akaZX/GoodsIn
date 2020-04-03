package app.controller.sql.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    <R> T get(R id);

    List<T> getAll();

    List<T> getAll(String param);

    boolean save(T t);

    boolean update(T t);

    boolean delete(T t);

}
