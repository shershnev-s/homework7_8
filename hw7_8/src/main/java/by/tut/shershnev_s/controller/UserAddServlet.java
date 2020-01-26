package by.tut.shershnev_s.controller;

import by.tut.shershnev_s.service.TableService;
import by.tut.shershnev_s.service.UserInformationService;
import by.tut.shershnev_s.service.UserService;
import by.tut.shershnev_s.service.impl.TableServiceImpl;
import by.tut.shershnev_s.service.impl.UserInformationServiceImpl;
import by.tut.shershnev_s.service.impl.UserServiceImpl;
import by.tut.shershnev_s.service.model.UserDTO;
import by.tut.shershnev_s.service.model.UserInformationDTO;
import by.tut.shershnev_s.service.model.UserWithIDDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;

public class UserAddServlet extends HttpServlet {

    private TableService tableService = TableServiceImpl.getInstance();
    private UserService userService = UserServiceImpl.getInstance();
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private UserInformationService userInformationService = UserInformationServiceImpl.getInstance();

    @Override
    public void init() throws ServletException {
        tableService.deleteAllTables();
        tableService.createAllTables();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "/create_user.html";
        RequestDispatcher view = getServletContext().getRequestDispatcher(forward);
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstname = req.getParameter("firstname");
        String password = req.getParameter("password");
        String isActive = req.getParameter("isActive");
        String age = req.getParameter("age");
        String address = req.getParameter("address");
        String telephone = req.getParameter("telephone");
        try {
            UserDTO userDTO = new UserDTO();
            userDTO.setFirstname(firstname);
            userDTO.setPassword(password);
            boolean isActiveBool = Boolean.parseBoolean(isActive);
            userDTO.setActive(isActiveBool);
            int ageInt = Integer.parseInt(age);
            userDTO.setAge(ageInt);
            UserWithIDDTO userWithIDDTO = userService.add(userDTO);
            UserInformationDTO userInformationDTO = new UserInformationDTO();
            userInformationDTO.setId(userWithIDDTO.getId());
            userInformationDTO.setAddress(address);
            userInformationDTO.setTelephone(telephone);
            userInformationService.add(userInformationDTO);
            try (PrintWriter out = resp.getWriter()) {
                out.println("<html><body>");
                out.println("User added");
                out.println("<br>");
                out.println("<br>");
                out.println("Username: " + userDTO.getFirstname());
                out.println("<br>");
                out.println("Password: " + userDTO.getPassword());
                out.println("<br>");
                out.println("Is active: " + userDTO.isActive());
                out.println("<br>");
                out.println("Age: " + userDTO.getAge());
                out.println("<br>");
                out.println("Address: " + userInformationDTO.getAddress());
                out.println("<br>");
                out.println("Telephone: " + userInformationDTO.getTelephone());
                out.println("</body></html>");
            }

        } catch (NumberFormatException e) {
            logger.error("Uncorrect format", e);
        }
    }
}
