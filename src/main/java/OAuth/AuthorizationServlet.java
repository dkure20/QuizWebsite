package OAuth;
import Database.DatabaseManager;
import OAuth.Models.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name="login", value="/HTML/login")
public class AuthorizationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        try {
            User currentUser = (User) DatabaseManager.userIsRegistered(username, password);
            if(currentUser!=null) {
                RequestDispatcher dis = req.getRequestDispatcher("/HTML/Homepage.jsp");
                req.setAttribute("user", currentUser);
                dis.forward(req, resp);
            } else {
                RequestDispatcher dis = req.getRequestDispatcher("/HTML/TryAgainAuthorization.jsp");
                dis.forward(req, resp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}