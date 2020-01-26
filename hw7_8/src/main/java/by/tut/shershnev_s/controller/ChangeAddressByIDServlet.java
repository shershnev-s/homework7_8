package by.tut.shershnev_s.controller;

import by.tut.shershnev_s.service.UserInformationService;
import by.tut.shershnev_s.service.impl.UserInformationServiceImpl;
import by.tut.shershnev_s.service.model.UserInformationDTO;
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

public class ChangeAddressByIDServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private UserInformationService userInformationService = UserInformationServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "/change_address.html";
        RequestDispatcher view = getServletContext().getRequestDispatcher(forward);
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("user_id");
        String address = req.getParameter("address");
        UserInformationDTO userInformationDTO = new UserInformationDTO();
        int id = Integer.parseInt(userId);
        userInformationDTO.setId(id);
        userInformationDTO.setAddress(address);
        userInformationService.updateByID(userInformationDTO);
        try (PrintWriter out = resp.getWriter()) {
            out.println("<html><body>");
            out.println("Address changed");
            out.println("<br>");
            out.println("New address: " + userInformationDTO.getAddress());
            out.println("<br>");
            out.println("</body></html>");
        } catch (NumberFormatException e) {
            logger.error("Uncorrect format", e);
        }

    }
}
