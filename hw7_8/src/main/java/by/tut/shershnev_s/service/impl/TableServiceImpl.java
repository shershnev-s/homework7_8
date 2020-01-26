package by.tut.shershnev_s.service.impl;

import by.tut.shershnev_s.repository.ConnectionRepository;
import by.tut.shershnev_s.repository.TableRepository;
import by.tut.shershnev_s.repository.enums.CreateActionEnum;
import by.tut.shershnev_s.repository.enums.DropActionEnum;
import by.tut.shershnev_s.repository.impl.ConnectionRepositoryImpl;
import by.tut.shershnev_s.repository.impl.TableRepositoryImpl;
import by.tut.shershnev_s.service.TableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;

public class TableServiceImpl implements TableService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static TableService instance;
    private TableRepository tableRepository = TableRepositoryImpl.getInstance();
    private ConnectionRepository connectionRepository = ConnectionRepositoryImpl.getInstance();

    public static TableService getInstance() {
        if (instance == null) {
            instance = new TableServiceImpl();
        }
        return instance;
    }

    @Override
    public void deleteAllTables() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (DropActionEnum action : DropActionEnum.values()) {
                    tableRepository.executeQuery(connection, action.getQuery());
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                logger.error("Cannot delete tables");

            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            logger.error("Cannot create connection");
        }
    }

    @Override
    public void createAllTables() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);

            try {
                for (CreateActionEnum action : CreateActionEnum.values()) {
                    tableRepository.executeQuery(connection, action.getQuery());
                }
                connection.commit();
                logger.info("Tables were created");
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                logger.error("Cannot create tables");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            logger.error("Cannot create connection");
        }
    }
}
