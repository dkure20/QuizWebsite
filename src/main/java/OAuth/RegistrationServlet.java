package OAuth;


import Database.DatabaseManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="register", value = "/HTML/register")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        ServletContext sc = req.getServletContext();

        try {
           if(DatabaseManager.addUser(username, password)) {
               RequestDispatcher dis = req.getRequestDispatcher("/HTML/Homepage.jsp");
               sc.setAttribute("username", username);
               dis.forward(req, resp);
           } else {
               RequestDispatcher dis = req.getRequestDispatcher("/HTML/TryAgainRegistration.jsp");
               sc.setAttribute("username", username);
               dis.forward(req, resp);
           }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
