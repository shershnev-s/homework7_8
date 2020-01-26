package by.tut.shershnev_s.service.impl;

import by.tut.shershnev_s.repository.ConnectionRepository;
import by.tut.shershnev_s.repository.UserInformationRepository;
import by.tut.shershnev_s.repository.impl.ConnectionRepositoryImpl;
import by.tut.shershnev_s.repository.impl.UserInformationRepositoryImpl;

import by.tut.shershnev_s.repository.model.UserInformation;
import by.tut.shershnev_s.service.UserInformationService;

import by.tut.shershnev_s.service.model.UserInformationDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserInformationServiceImpl implements UserInformationService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static UserInformationService instance;
    private ConnectionRepository connectionRepository = ConnectionRepositoryImpl.getInstance();
    private UserInformationRepository userInformationRepository = UserInformationRepositoryImpl.getInstance();

    public static UserInformationService getInstance() {
        if (instance == null) {
            instance = new UserInformationServiceImpl();
        }
        return instance;
    }

    @Override
    public UserInformationDTO add(UserInformationDTO userInformationDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                UserInformation userInformation = convertDTOToUserInformation(userInformationDTO);
                userInformation = userInformationRepository.add(connection, userInformation);
                connection.commit();
                return convertUserInformationToDTO(userInformation);
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                logger.error("Can't add User Information");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            logger.error("Can't create connection");
        }
        return null;
    }

    @Override
    public List<UserInformationDTO> findAll() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            List<UserInformationDTO> UserInformationDTODTOS = new ArrayList<>();
            try {
                List<UserInformation> userInformation = userInformationRepository.findAll(connection);
                connection.commit();
                UserInformationDTO userInformationDTO;
                for (UserInformation element : userInformation) {
                    userInformationDTO = convertUserInformationToDTO(element);
                    UserInformationDTODTOS.add(userInformationDTO);
                }
                return UserInformationDTODTOS;
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
    public void updateByID(UserInformationDTO userInformationDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Integer id = userInformationDTO.getId();
                String address = userInformationDTO.getAddress();
                userInformationRepository.updateByID(connection, id, address);
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

    private UserInformation convertDTOToUserInformation(UserInformationDTO userInformationDTO) {
        UserInformation userInformation = new UserInformation();
        userInformation.setUserId(userInformationDTO.getId());
        userInformation.setAddress(userInformationDTO.getAddress());
        userInformation.setTelephone(userInformationDTO.getTelephone());
        return userInformation;
    }

    private UserInformationDTO convertUserInformationToDTO(UserInformation userInformation) {
        UserInformationDTO userInformationDTO = new UserInformationDTO();
        userInformationDTO.setAddress(userInformation.getAddress());
        userInformationDTO.setTelephone(userInformation.getTelephone());

        return userInformationDTO;
    }
}
