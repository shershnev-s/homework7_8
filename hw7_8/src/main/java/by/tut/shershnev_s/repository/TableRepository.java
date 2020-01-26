package by.tut.shershnev_s.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface TableRepository {

    void executeQuery(Connection connection, String query) throws SQLException;

}
