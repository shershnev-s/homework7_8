package by.tut.shershnev_s.repository.impl;

import by.tut.shershnev_s.repository.TableRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableRepositoryImpl implements TableRepository {

    private static TableRepository instance;

    public static TableRepository getInstance() {
        if (instance == null) {
            instance = new TableRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void executeQuery(Connection connection, String query) throws SQLException {
        try (
                Statement statement = connection.createStatement()
        ) {
            statement.execute(query);
        }
    }
}
