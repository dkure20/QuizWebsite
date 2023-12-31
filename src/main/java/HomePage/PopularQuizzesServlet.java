package HomePage;

import Database.DatabaseManager;
import Quiz.Models.Quiz;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "PopularQuizzesServlet", value = "/PopularQuizzesServlet")
public class PopularQuizzesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("searchQuery");
        List<Quiz> quizzes = DatabaseManager.getAllQuizzes();
        quizzes.sort(Comparator.comparingInt(Quiz::getSubmissionCount).reversed());

        request.setAttribute("popularQuizzes", quizzes);
        RequestDispatcher dis = request.getRequestDispatcher("/HTML/HomePage/seeMorePopularQuizzes.jsp");
        dis.forward(request, response);
    }
}
