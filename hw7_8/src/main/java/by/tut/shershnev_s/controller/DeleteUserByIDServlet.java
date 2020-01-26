package by.tut.shershnev_s.controller;

import by.tut.shershnev_s.service.UserService;
import by.tut.shershnev_s.service.impl.UserServiceImpl;
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

public class DeleteUserByIDServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "/delete.html";
        RequestDispatcher view = getServletContext().getRequestDispatcher(forward);
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("user_id");
        UserWithIDDTO userWithIDDTO = new UserWithIDDTO();
        Integer id = Integer.parseInt(userId);
        userWithIDDTO.setId(id);
        userService.deleteByID(userWithIDDTO);
        try (PrintWriter out = resp.getWriter()) {
            out.println("<html><body>");
            out.println("User was deleted");
            out.println("<br>");
            out.println("ID deleted user: " + userWithIDDTO.getId());
            out.println("<br>");
            out.println("</body></html>");
        } catch (NumberFormatException e) {
            logger.error("Uncorrect format", e);
        }
    }
}
