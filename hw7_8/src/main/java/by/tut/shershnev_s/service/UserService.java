package by.tut.shershnev_s.service;

import by.tut.shershnev_s.service.model.UserDTO;
import by.tut.shershnev_s.service.model.UserWithIDDTO;

import java.util.List;

public interface UserService {

    UserWithIDDTO add(UserDTO userDTO);

    List<UserWithIDDTO> findAll();

    void deleteByID(UserWithIDDTO userWithIDDTO);

}
