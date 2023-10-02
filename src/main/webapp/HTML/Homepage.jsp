<%@ page import="OAuth.Models.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(-45deg, #fdb8a0, #f5a0bf, #7ab7d0, #88d2c0);
            animation: gradient 15s ease infinite;
        }

        @keyframes gradient {
            0% {
                background-position: 0% 50%;
            }

            50% {
                background-position: 100% 50%;
            }

            100% {
                background-position: 0% 50%;
            }
        }

        header {
            background-color: #b115a9;
            color: #ffffff;
            padding: 10px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .top-left {
            font-weight: bold;
        }

        .action-buttons {
            display: flex;
            gap: 10px;
        }

        .action-button {
            background-color: #b115a9;
            color: #ffffff;
            padding: 8px 16px;
            text-decoration: none;
            border-radius: 4px;
            transition: background-color 0.2s;
        }

        .action-button:hover {
            background-color: #0056b3;
        }

        .announcements-table {
            width: 30px;
            margin: 20px 0;
            border-collapse: collapse;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
            float: left;
        }

        .announcements-table th,
        .announcements-table td {
            border: 1px solid #efe597;
            padding: 12px;
            text-align: center;
        }

        .announcements-table th {
            background-color: #efe597;
        }

        .announcements-table tr:hover {
            background-color: #efe597;
        }

        .box {
            width: 80px;
            height: 140px;
            margin: 20px;
            padding: 10px;
            border: 1px solid #ddd;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
            float: left;
            background-color: #efe597;
            display: flex;
            flex-direction: column;
        }

        .box-title {
            font-weight: bold;
            margin-bottom: 10px;
        }

        .box-content {
            margin-bottom: 10px;
            flex-grow: 1;
        }

        .see-more-button {
            margin-top: 10px;
            align-self: flex-end;
            background-color: #b115a9;
            color: #ffffff;
            padding: 6px 12px;
            text-decoration: none;
            border-radius: 4px;
            transition: background-color 0.2s;
        }

        .see-more-button:hover {
            background-color: #b115a9;
        }

        .big-box {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            padding: 10px;
            border: 1px solid #030303;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
            background-color: #efe597;
            margin: 20px;
        }

        .box {
            flex: 1 0 40%;
            padding: 5px;
            border: 1px solid #ddd;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
            background-color: #f9f9f9;
        }

        @media (max-width: 768px) {
            .big-box {
                flex-direction: column;
            }
        }
    </style>
</head>
<body>
<header>
    <div class="top-left">
        <% User currentUser = (User) request.getAttribute("user");
            session.setAttribute("user", currentUser);
        %>
        Welcome, <%= currentUser.getUsername() %>!
    </div>
    <div class="action-buttons">
        <a class="action-button" href="${pageContext.request.contextPath}/LogOutServlet">Log out</a>
    </div>
</header>
<table class="announcements-table">
    <tr>
        <th>Main</th>
    </tr>
    <tr>
        <td>
            <a class="action-button"
               href="${pageContext.request.contextPath}/ShowAnnouncementsServlet">Announcements</a>
        </td>
    </tr>
    <tr>
        <td>
            <a class="action-button" href="${pageContext.request.contextPath}/HTML/notifications.jsp">Notifications</a>
        </td>
    </tr>
    <tr>
        <td>
            <a class="action-button" href="${pageContext.request.contextPath}/HTML/QuizCreation.jsp">Create Quiz</a>
        </td>
    </tr>
    <tr>
        <td>
            <a class="action-button" href="${pageContext.request.contextPath}/HTML/Friends.jsp">Friends</a>
        </td>
    <tr>
        <td>
            <a class="action-button" href="${pageContext.request.contextPath}/HTML/Admin/AdminJsp.jsp">Admin Page</a>
        </td>
    </tr>
</table>

<div class="big-box">
    <div class="box">
        <div class="box-title">Popular Quizzes</div>
        <div class="box-content">

        </div>
        <a class="see-more-button" href="${pageContext.request.contextPath}/HTML/HomePage/seeMorePopularQuizzes.jsp">See
            More</a>
    </div>

    <div class="box">
        <div class="box-title">Recently Created Quizzes</div>
        <div class="box-content">
        </div>
        <a class="see-more-button"
           href="${pageContext.request.contextPath}/HTML/HomePage/seeMoreRecentlyCreatedQuizzes.jsp">See More</a>
    </div>

    <div class="box">
        <div class="box-title">Quizzes I Took</div>
        <div class="box-content">
        </div>
        <a class="see-more-button" href="${pageContext.request.contextPath}/HTML/HomePage/seeMoreQuizzesITook.jsp">See
            More</a>
    </div>

    <div class="box">
        <div class="box-title">Quizzes I Created Recently</div>
        <div class="box-content">
        </div>
        <a class="see-more-button"
           href="${pageContext.request.contextPath}/HTML/HomePage/seeMoreQuizzesICreatedRecently.jsp">See More</a>
    </div>
    <div class="box">
        <div class="box-title">My Achievements</div>
        <div class="box-content">
        </div>
        <a class="see-more-button" href="${pageContext.request.contextPath}/HTML/seeMoreQuizzesICreated.jsp">See
            More</a>
    </div>
    <div class="box">
        <div class="box-title">Search for Users</div>
        <div class="box-content">
            <form method="post" action="${pageContext.request.contextPath}/HTML/UserSearchServlet">
                <label for="searchField">Search Friends:</label>
                <input type="text" id="searchField" name="searchQuery">
                <button type="submit">Find</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>
