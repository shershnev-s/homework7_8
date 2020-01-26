package by.tut.shershnev_s.repository;

import by.tut.shershnev_s.repository.model.UserInformation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserInformationRepository extends GeneralRepository<UserInformation> {

    UserInformation add(Connection connection, UserInformation userInformation) throws SQLException;

    List<UserInformation> findAll(Connection connection) throws SQLException;

    void updateByID(Connection connection, Integer id, String address) throws SQLException;

}
