<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="OAuth.Models.User" %>
<%@ page import="Database.DatabaseManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="Mails.Mail" %>
<!DOCTYPE html>
<html>
<%
    User currentUser = (User) session.getAttribute("user");
    boolean areFriends = false;
    List<User> FriendRequestSent;
    try {
        FriendRequestSent = DatabaseManager.GetFriendsWithReqs(currentUser.getId());
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    List<User> currentUserFriends;
    try {
        currentUserFriends = DatabaseManager.GetUserFriends(currentUser.getId());
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    List<Mail> incomingMails;
    try {
        incomingMails = DatabaseManager.GetUserNotifications(currentUser.getId());
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    int userId = Integer.parseInt(request.getParameter("userId"));
    User user = null;
    try {
        user = DatabaseManager.getUser(userId);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    boolean requestSend = false;
    Mail correctMail = null;
    for (Mail mail : incomingMails) {
        if (mail.getFromId() == userId && mail.getType().equals("FRT")){
            requestSend = true;
            correctMail = mail;
        }

    }
    if (currentUserFriends.contains(user)) {
        areFriends = true;
    }
    boolean currentUserAlreadySent = FriendRequestSent.contains(user);
%>
<head>
    <meta charset="UTF-8">
    <title>Welcome to <%= user.getUsername() %>'s Page</title>
    <style>
        html, body {
            height: 100%;
            margin: 0;
        }
        body {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            background: linear-gradient(-45deg, #fdb8a0, #f5a0bf, #7ab7d0, #88d2c0);
            background-size: 400% 400%;
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
        h1 {
            margin: 0;
            padding-top: 20px;
            color: #333;
            text-align: center;
        }
        h2 {
            color: #555;
            text-align: center;
        }
        table {
            margin: 20px auto;
            border-collapse: collapse;
            width: 80%;
            max-width: 800px;
            background-color: #fff;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center; /* Center-align the content */
        }
        th {
            background-color: #f2f2f2;
        }
        .scrollable-table {
            border-collapse: collapse;
            width: 80%;
            max-width: 800px;
            max-height: 300px; /* Adjust the maximum height as needed */
            overflow-y: auto;
        }
        .button-container {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }
        .action-button {
            display: inline-block;
            padding: 10px 20px;
            margin: 0 10px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .action-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<h1>Welcome to <%= user.getUsername() %>'s Page</h1>

<div class="button-container">
    <%if(currentUser.getId()!= userId){
        if (areFriends) { %>
    <button class="action-button">Friends</button>
    <% } else if (requestSend) { %>
    <form method="post" action="../AcceptFriendRequestServlet">
        <input type="hidden" name="mailId" value="<%= correctMail.getId() %>">
        <input type="hidden" name="fromId" value="<%= correctMail.getFromId() %>">
        <button type="submit" class="action-button yes-button">Yes</button>
    </form>
    <form method="post" action="../RemoveFriendRequestServlet">
        <input type="hidden" name="mailId" value="<%= correctMail.getId() %>">
        <button type="submit" class="action-button yes-button">No</button>
    </form>
    <% } else if (currentUserAlreadySent) { %>
    <button class="action-button">Already Sent</button>
    <% } else { %>
    <form method="post" action="../AddFriendServlet">
        <input type="hidden" name="friendId" value="<%= user.getId() %>">
        <button type="submit" class="action-button">Send Friend Request</button>
    </form>
    <% }
    }
    %>

</div>

<h2>Friend List</h2>
<div class="scrollable-table">
    <table>
        <tr>
            <th>Friend Name</th>
        </tr>
        <%
            List<User> friendList = null;
            try {
                friendList = DatabaseManager.GetUserFriends(user.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }%>
        <% for (User friend : friendList) {
            if(friend.getId() == currentUser.getId()){

            }else{%>
        <tr>
            <td><a href="${pageContext.request.contextPath}/HTML/ViewProfile.jsp?userId=<%=friend.getId()%>" class="action-link"><%=friend.getUsername()%></a></td>
        </tr>
        <% }
        }%>
    </table>
</div>

<h2>Quizzes Created</h2>
<table>
    <tr>
        <th>Quiz Name</th>
    </tr>
    <tr>
        <td>quiz</td>
    </tr>
</table>

<h2>Achievements</h2>
<table>
    <tr>
        <th>Achievement</th>
        <th>Description</th>
    </tr>
    <tr>
        <td></td>
        <td></td>
    </tr>
</table>
</body>
</html>
