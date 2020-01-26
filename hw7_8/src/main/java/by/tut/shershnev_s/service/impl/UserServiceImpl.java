package by.tut.shershnev_s.service.impl;

import by.tut.shershnev_s.repository.ConnectionRepository;
import by.tut.shershnev_s.repository.UserRepository;
import by.tut.shershnev_s.repository.impl.ConnectionRepositoryImpl;
import by.tut.shershnev_s.repository.impl.UserRepositoryImpl;
import by.tut.shershnev_s.repository.model.User;
import by.tut.shershnev_s.service.UserService;
import by.tut.shershnev_s.service.model.UserDTO;
import by.tut.shershnev_s.service.model.UserWithIDDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static UserService instance;
    private ConnectionRepository connectionRepository = ConnectionRepositoryImpl.getInstance();
    private UserRepository userRepository = UserRepositoryImpl.getInstance();

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public UserWithIDDTO add(UserDTO userDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = convertDTOToUser(userDTO);
                user = userRepository.add(connection, user);
                connection.commit();
                return convertUserToDTO(user);
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                logger.error("Can't add user");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            logger.error("Can't create connection");
        }
        return null;
    }

    @Override
    public List<UserWithIDDTO> findAll() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            List<UserWithIDDTO> userDTOS = new ArrayList<>();
            try {
                List<User> people = userRepository.findAll(connection);
                connection.commit();
                UserWithIDDTO userDTO;
                for (User person : people) {
                    userDTO = convertUserToDTO(person);
                    userDTOS.add(userDTO);
                }
                return userDTOS;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                logger.error("Can't find user");
            }
        } catch (
                SQLException e) {
            logger.error(e.getMessage(), e);
            logger.error("Can't create connection");
        }
        return null;
    }

    @Override
    public void deleteByID(UserWithIDDTO userWithIDDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Integer id = userWithIDDTO.getId();
                userRepository.deleteByID(connection, id);
                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                logger.error("Can't updateByID User Information");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            logger.error("Can't create connection");
        }
    }

    private User convertDTOToUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getFirstname());
        user.setPassword(userDTO.getPassword());
        user.setActive(userDTO.isActive());
        user.setAge(userDTO.getAge());
        return user;
    }

    private UserWithIDDTO convertUserToDTO(User user) {
        UserWithIDDTO userDTO = new UserWithIDDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setActive(user.isActive());
        userDTO.setAge(user.getAge());
        return userDTO;
    }

}
