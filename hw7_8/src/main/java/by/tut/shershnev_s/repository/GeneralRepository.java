package by.tut.shershnev_s.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface GeneralRepository<T> {

    T add(Connection connection, T t) throws SQLException;

}
