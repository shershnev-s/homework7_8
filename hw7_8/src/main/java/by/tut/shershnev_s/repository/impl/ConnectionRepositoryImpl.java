package by.tut.shershnev_s.repository.impl;

import by.tut.shershnev_s.repository.ConnectionRepository;
import by.tut.shershnev_s.repository.constant.ConnectionConstant;
import by.tut.shershnev_s.util.PropertyUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static by.tut.shershnev_s.repository.constant.ConnectionConstant.*;

public class ConnectionRepositoryImpl implements ConnectionRepository {

    private static final String CACHE_PREP_STMTS = "cachePrepStmts";
    private static final String PREP_STMT_CACHE_SIZE = "prepStmtCacheSize";
    private static final String PREP_STMT_CACHE_SQL_LIMIT = "prepStmtCacheSqlLimit";
    private static HikariDataSource ds;
    private static ConnectionRepository instance;

    private ConnectionRepositoryImpl() {
    }

    public static ConnectionRepository getInstance() {
        if (instance == null) {
            instance = new ConnectionRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (ds == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            PropertyUtil propertyUtil = new PropertyUtil();
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(propertyUtil.getProperty(DATABASE_URL));
            config.setUsername(propertyUtil.getProperty(DATABASE_USERNAME));
            config.setPassword(propertyUtil.getProperty(DATABASE_PASSWORD));
            config.addDataSourceProperty(CACHE_PREP_STMTS, propertyUtil.getProperty(ConnectionConstant.CACHE_PREP_STMTS));
            config.addDataSourceProperty(PREP_STMT_CACHE_SIZE, propertyUtil.getProperty(ConnectionConstant.PREP_STMT_CACHE_SIZE));
            config.addDataSourceProperty(PREP_STMT_CACHE_SQL_LIMIT, propertyUtil.getProperty(ConnectionConstant.PREP_STMT_CACHE_SQL_LIMIT));
            ds = new HikariDataSource(config);
        }
        return ds.getConnection();
    }
}
