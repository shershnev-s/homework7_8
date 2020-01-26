package by.tut.shershnev_s.controller;

import by.tut.shershnev_s.service.UserInformationService;
import by.tut.shershnev_s.service.UserService;
import by.tut.shershnev_s.service.impl.UserInformationServiceImpl;
import by.tut.shershnev_s.service.impl.UserServiceImpl;
import by.tut.shershnev_s.service.model.UserInformationDTO;
import by.tut.shershnev_s.service.model.UserWithIDDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class PrintAllUserServlet extends HttpServlet {

    private UserService userService = UserServiceImpl.getInstance();
    private UserInformationService userInformationService = UserInformationServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserWithIDDTO> userDTOS = userService.findAll();
        List<UserInformationDTO> userInformationDTOS = userInformationService.findAll();
        try (PrintWriter out = resp.getWriter()) {
            out.println("<html><body>");
            out.println("Users:");
            for (int i = 0; i < userDTOS.size(); i++) {
                out.println("<br>");
                out.println("<br>");
                out.println("Username: " + userDTOS.get(i).getFirstname());
                out.println("<br>");
                out.println("Password: " + userDTOS.get(i).getPassword());
                out.println("<br>");
                out.println("<td>" + "Is active: " + userDTOS.get(i).isActive());
                out.println("<br>");
                out.println("<td>" + "Age: " + userDTOS.get(i).getAge());
                out.println("<br>");
                out.println("<td>" + "Address: " + userInformationDTOS.get(i).getAddress());
                out.println("<br>");
                out.println("<td>" + "Telephone: " + userInformationDTOS.get(i).getTelephone());
                out.println("<br>");
                out.println("<br>");
            }
            out.println("</body></html>");
        }
    }
}
