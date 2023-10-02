<%@ page import="Database.DatabaseManager" %>
<%@ page import="Quiz.Models.Question" %>
<%@ page import="Quiz.Models.Quiz" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quiz Front Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh; /* Ensure the content is centered within the viewport */
        }

        .container {
            max-width: 600px;
            padding: 40px;
            border: 1px solid #ccc;
            border-radius: 8px;
            background-color: #ffffff;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            text-align: center; /* Center the text within the container */
        }

        h1 {
            font-size: 24px;
            color: #333;
            margin-bottom: 20px;
        }

        p {
            font-size: 16px;
            color: #555;
        }

        button.start-button {
            padding: 12px 24px;
            font-size: 18px;
            background-color: #DE3163;
            color: #ffffff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s;
        }


        button.start-button:hover {
            background-color: #F33A6A;
        }
    </style>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <%
        Quiz quiz;
        try {
            quiz = DatabaseManager.GetQuizById(Integer.parseInt(request.getParameter("id")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getSession().setAttribute("curQuiz", quiz);
        String quizName = quiz.getName();
        String quizSummary = quiz.getDescription();
    %>
</head>
<body>
<div class="container">
    <h1>Quiz: <%=quizName%>
    </h1>
    <p><%=quizSummary%>
    </p>
    <form action="${pageContext.request.contextPath}/QuizFrontPageServlet" method="post">
        <input type="hidden" name="quizName" value=<%=quizName%>>
        <input type="hidden" name="quizSummary" value=<%=quizSummary%>>
        <button type="submit" class="start-button">Start Quiz</button>
    </form>
</div>
</body>
</html>
