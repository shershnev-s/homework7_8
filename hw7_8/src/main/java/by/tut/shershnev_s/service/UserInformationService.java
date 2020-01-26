package by.tut.shershnev_s.service;

import by.tut.shershnev_s.service.model.UserInformationDTO;

import java.util.List;

public interface UserInformationService {

    UserInformationDTO add(UserInformationDTO userInformationDTO);

    List<UserInformationDTO> findAll();

    void updateByID(UserInformationDTO userInformationDTO);

}
