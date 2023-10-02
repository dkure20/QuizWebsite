package Database;

import Admin.Models.AnnouncementUserModel;
import Mails.Challenge;
import Mails.FriendRequestMail;
import Mails.Mail;
import Mails.Note;
import OAuth.Models.User;
import Quiz.Models.*;
import QuizPage.Models.DataRetrieve;
import QuizPage.Models.Result;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static Connection connection;
    private static Statement statement;

    public static void runDatabaseCreationScript() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
            statement = connection.createStatement();

            BufferedReader reader = new BufferedReader(new FileReader(DatabaseConstant.SCRIPT_FILE_PATH));
            String line = null;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();

            String[] sqlCommands = stringBuilder.toString().split(";");

            for (String sqlCommand : sqlCommands) {
                statement.executeUpdate(sqlCommand);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean addUser(String username, String password) throws SQLException {
        ResultSet rs = statement.executeQuery(
                "SELECT * FROM Users where Username = '" + username + "'");

        if (rs.next()) return false;

        String sql = "INSERT INTO Users" + " (Username, Password) VALUES (?, ?)";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, username);
        st.setString(2, password);
        st.executeUpdate();

        return true;
    }

    public static User userIsRegistered(String username, String password) throws SQLException {
        String query = "SELECT id, username, password FROM users WHERE username = ? AND password = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setString(1, username);
        st.setString(2, password);
        ResultSet resultSet = st.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String userName = resultSet.getString("Username");
            return new User(id, userName);
        }
        return null;
    }

    public static Quiz AddQuiz(Quiz quiz) throws SQLException {
        PreparedStatement st = connection.prepareStatement("INSERT INTO quizzes (name , description, creator_id , has_random_questions, is_one_page, is_immediate_correction , has_practice_mode )" +
                "VALUES (?, ?, ?, ?,?,?,?);", Statement.RETURN_GENERATED_KEYS);

        st.setString(1, quiz.getName());
        st.setString(2, quiz.getDescription());
        st.setInt(3, quiz.getCreatorId());
        st.setBoolean(4, quiz.hasRandomQuestions());
        st.setBoolean(5, quiz.isOnePage());
        st.setBoolean(6, quiz.isImmediateCorrection());
        st.setBoolean(7, quiz.hasPracticeMode());

        int affectedRows = st.executeUpdate();

        if (affectedRows == 0) {
            throw new RuntimeException("Failed to insert quiz");
        }

        ResultSet generatedKeys = st.getGeneratedKeys();
        generatedKeys.next();
        int generatedId = generatedKeys.getInt(1);
        st.close();

        return GetQuizById(generatedId);
    }

    public static void updateCreationDateAndTime(int quizId) {
        String query = "UPDATE quizzes SET last_visited = ? WHERE id = ?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            Timestamp visitedTimestamp = new Timestamp(System.currentTimeMillis());

            preparedStatement.setTimestamp(1, visitedTimestamp);
            preparedStatement.setInt(2, quizId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateSubmissionCount(int quizId, int count) {
        String query = "UPDATE quizzes SET submission_count = ? WHERE id = ?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, count);
            preparedStatement.setInt(2, quizId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Quiz GetQuizById(int id) throws SQLException {
        PreparedStatement st = connection.prepareStatement("SELECT * FROM quizzes WHERE Id = ?;");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        if (!rs.next()) return null;

        Quiz quiz = new Quiz(rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getInt(4),
                rs.getBoolean(5),
                rs.getBoolean(6),
                rs.getBoolean(7),
                rs.getBoolean(8),
                rs.getTimestamp(9),
                rs.getInt(10));
        st.close();
        return quiz;
    }

    public static int InsertQuestion(Question question, int quizId) throws SQLException {
        PreparedStatement st = connection.prepareStatement("INSERT INTO questions (quiz_id, question_id, question_type, content)" +
                "VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);

        st.setInt(1, quizId);
        st.setInt(2, question.getId());
        st.setInt(3, question.getType().ordinal());
        st.setString(4, question.getContent());

        int affectedRows = st.executeUpdate();

        if (affectedRows == 0) {
            throw new RuntimeException("Failed to insert quiz");
        }

        ResultSet generatedKeys = st.getGeneratedKeys();
        generatedKeys.next();
        int generatedId = generatedKeys.getInt(1);

        return generatedId;
    }

    public static Answer InsertMultipleChoiceAnswer(MultipleChoiceAnswer answer, int questionId) throws SQLException {
        PreparedStatement st = connection.prepareStatement("INSERT INTO answers (question_id, content, is_correct)" +
                "VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);

        st.setInt(1, questionId);
        st.setString(2, answer.getContent());
        st.setBoolean(3, answer.isTrue());

        int affectedRows = st.executeUpdate();

        if (affectedRows == 0) {
            throw new RuntimeException("Failed to insert quiz");
        }

        ResultSet generatedKeys = st.getGeneratedKeys();
        generatedKeys.next();
        int generatedId = generatedKeys.getInt(1);

        return new MultipleChoiceAnswer(questionId, answer.getContent(), answer.isTrue());
    }

    public static Answer InsertCorrectAnswer(CorrectAnswers answer, int questionId) throws SQLException {
        PreparedStatement st = connection.prepareStatement("INSERT INTO answers (question_id, content)" +
                "VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);

        st.setInt(1, questionId);
        st.setString(2, answer.getContent());

        int affectedRows = st.executeUpdate();

        if (affectedRows == 0) {
            throw new RuntimeException("Failed to insert quiz");
        }

        ResultSet generatedKeys = st.getGeneratedKeys();
        generatedKeys.next();
        int generatedId = generatedKeys.getInt(1);

        return new CorrectAnswers(questionId, answer.getContent());
    }


    public static List<Question> getQuestionById(int quizID) throws SQLException {
        // Questions of the quiz
        List<Question> questions = DataRetrieve.queryQuestions(quizID, connection);
        assert questions != null;
        for (Question q : questions) {
            // Answers of the question
            List<MultipleChoiceAnswer> answers = DataRetrieve.queryAnswers(q, connection);
            // add answers to questions
            DataRetrieve.appendAnswersToQuestion(q, answers);
        }
        return questions;
    }

    public static List<User> GetUserFriends(int userId) throws SQLException {
        List<User> userFriends = new ArrayList<>();
        String query = "SELECT l.friend_id, u.Username " +
                "FROM (" +
                "    SELECT " +
                "        CASE " +
                "            WHEN first_person = ? THEN second_person " +
                "            ELSE first_person " +
                "        END AS friend_id " +
                "    FROM " +
                "        tank_database.friends " +
                "    WHERE " +
                "        first_person = ? OR second_person = ?" +
                ") AS l " +
                "LEFT JOIN tank_database.users u ON l.friend_id = u.Id";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, userId);
        st.setInt(2, userId);
        st.setInt(3, userId);
        ResultSet resultSet = st.executeQuery();
        while (resultSet.next()) {
            int friendId = resultSet.getInt("friend_id");
            String username = resultSet.getString("Username");
            userFriends.add(new User(friendId, username));
        }
        return userFriends;
    }


    public static List<User> GetAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "select Id, Username from users";
        PreparedStatement st = connection.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            int userId = rs.getInt("Id");
            String username = rs.getString("Username");
            User user = new User(userId, username);
            users.add(user);
        }
        return users;
    }

    public static void SendMail(int id, int toUserId, String messageType, String title, String content, Date date) throws SQLException {
        String query = "INSERT INTO mails (from_Id, to_Id, message_type, message_title, message_content, send_date) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.setInt(2, toUserId);
        statement.setString(3, messageType);
        statement.setString(4, title);
        statement.setString(5, content);
        statement.setDate(6, date);
        statement.executeUpdate();

    }

    public static List<Mail> GetUserNotifications(int id) throws SQLException {
        List<Mail> IncomingMails = new ArrayList<>();
        String query = "SELECT * FROM mails WHERE to_Id = ? ORDER BY send_date DESC;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Mail incoming = parseMail(rs);
            IncomingMails.add(incoming);
        }
        return IncomingMails;
    }

    private static Mail parseMail(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        int fromId = rs.getInt(2);
        int toId = rs.getInt(3);
        String mailType = rs.getString(4);
        String title = rs.getString(5);
        String content = rs.getString(6);
        Date time = rs.getDate(7);
        if (mailType.equals("NOTE")) {
            return new Note(id, fromId, toId, title, content, time);
        } else if (mailType.equals("FRT")) {
            return new FriendRequestMail(id, fromId, toId, time);
        } else if (mailType.equals("CH")) {
            return new Challenge(id, fromId, toId, title, content, time);
        }
        return null;
    }

    public static User getUser(int fromId) throws SQLException {
        String query = "SELECT id, username, password FROM users WHERE Id = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, fromId);
        ResultSet resultSet = st.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String userName = resultSet.getString("Username");
            return new User(id, userName);
        }
        return null;
    }

    public static void MakeFriends(int first, int second) throws SQLException {
        String insertQuery = "INSERT INTO friends (first_person, second_person) VALUES (?, ?)";
        PreparedStatement st = connection.prepareStatement(insertQuery);
        st.setInt(1, first);
        st.setInt(2, second);
        st.executeUpdate();


    }

    public static void DeleteMail(int mailId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM mails WHERE Id = ?");
        statement.setInt(1, mailId);
        statement.execute();
    }

    public static List<User> GetFriendsWithReqs(int userId) throws SQLException {
        List<User> userFriends = new ArrayList<>();
        String query = "SELECT m.to_id, u.username\n" +
                "FROM tank_database.mails m\n" +
                "LEFT JOIN tank_database.users u ON u.id = m.to_id\n" +
                "WHERE m.from_id = ? AND m.message_type = \"FRT\";";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, userId);
        ResultSet resultSet = st.executeQuery();
        while (resultSet.next()) {
            int friendId = resultSet.getInt("to_id");
            String username = resultSet.getString("username");
            userFriends.add(new User(friendId, username));
        }
        return userFriends;

    }

    private static Quiz parseQuiz(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        String name = rs.getString(2);
        String description = rs.getString(3);
        int creatorId = rs.getInt(4);
        boolean hasRandomQuestions = rs.getBoolean(5);
        boolean isOnePage = rs.getBoolean(6);
        boolean isImmediateCorrection = rs.getBoolean(7);
        boolean hasPracticeMode = rs.getBoolean(8);
        Timestamp lastVisited = rs.getTimestamp(9);
        int count = rs.getInt(10);
        return new Quiz(id, name, description, creatorId, hasRandomQuestions, isOnePage, isImmediateCorrection, hasPracticeMode, lastVisited, count);
    }

    public static List<Quiz> getAllQuizes() throws SQLException {
        List<Quiz> quizzes = new ArrayList<>();
        String query = "SELECT * FROM quizzes";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Quiz quiz = parseQuiz(rs);
            quizzes.add(quiz);
        }
        return quizzes;
    }

    public static void DeleteQuiz(int quizId) throws SQLException {

        PreparedStatement deleteAnswers = connection.prepareStatement("DELETE FROM answers WHERE question_id in " +
                "(SELECT question_id from questions where quiz_id = ?)");
        deleteAnswers.setInt(1, quizId);
        deleteAnswers.execute();

        PreparedStatement deleteQuestionStatement = connection.prepareStatement("DELETE FROM questions WHERE quiz_id = ?");
        deleteQuestionStatement.setInt(1, quizId);
        deleteQuestionStatement.execute();

        PreparedStatement deleteQuiz = connection.prepareStatement("DELETE FROM quizzes WHERE Id = ?");
        deleteQuiz.setInt(1, quizId);
        deleteQuiz.execute();

    }

    public static void addAnnouncement(String announcement, int adminId) throws SQLException {
        String query = "INSERT INTO announcements (admin_id, announcement)" +
                "VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, adminId);
        statement.setString(2, announcement);
        statement.executeUpdate();
    }

    public static int getNumberOfUsers() throws SQLException {
        String query = "Select Count(*) as num_users from users where is_admin = false";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int numberOfUsers = resultSet.getInt("num_users");
        return numberOfUsers;
    }

    public static List<User> GetAllNonAdminUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "select Id, Username from users where is_admin = false";
        PreparedStatement st = connection.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            int userId = rs.getInt("Id");
            String username = rs.getString("Username");
            User user = new User(userId, username);
            users.add(user);
        }
        return users;
    }

    public static void DeleteUser(int userId) throws SQLException {

        PreparedStatement deleteAnswers = connection.prepareStatement("DELETE FROM answers WHERE question_id in " +
                "(SELECT question_id from questions where quiz_id in " +
                "(SELECT id from quizzes where creator_id = ?))");
        deleteAnswers.setInt(1, userId);
        deleteAnswers.execute();

        PreparedStatement deleteQuestionStatement = connection.prepareStatement("DELETE from questions where quiz_id in " +
                "(SELECT id from quizzes where creator_id = ?)");
        deleteQuestionStatement.setInt(1, userId);
        deleteQuestionStatement.execute();

        PreparedStatement deleteQuiz = connection.prepareStatement("DELETE from quizzes where creator_id = ?");
        deleteQuestionStatement.setInt(1, userId);
        deleteQuestionStatement.execute();

        PreparedStatement deleteUser = connection.prepareStatement("DELETE FROM users WHERE Id = ?");
        deleteUser.setInt(1, userId);
        deleteUser.execute();
    }

    public static void promoteUser(int userId) throws SQLException {
        PreparedStatement updateQuery = connection.prepareStatement(
                "Update users " +
                        "SET is_admin = true where Id = ?"
        );
        updateQuery.setInt(1, userId);
        updateQuery.executeUpdate();
    }

    public static void insertQuizResult(Result result) throws SQLException {
        PreparedStatement st = connection.prepareStatement("INSERT INTO quiz_results (quiz_id, user_id, score, percent, quiz_duration, finish_time)" +
                "VALUES (?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);

        st.setInt(1, result.getQuizId());
        st.setInt(2, result.getUserId());
        st.setInt(3, result.getScore());
        st.setInt(4, result.getPercent());
        st.setInt(5, result.getDuration());
        st.setTimestamp(6, result.getFinishTime());

        int affectedRows = st.executeUpdate();

        if (affectedRows == 0) {
            throw new RuntimeException("Failed to insert quiz result");
        }

        ResultSet generatedKeys = st.getGeneratedKeys();
        generatedKeys.next();
        int generatedId = generatedKeys.getInt(1);
    }

    public static List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        String query = "SELECT q.id" +
                " FROM tank_database.quizzes q";
        PreparedStatement st;
        List<Integer> quizzIds = new ArrayList<>();
        try {
            st = connection.prepareStatement(query);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                quizzIds.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Integer quizId : quizzIds) {
            Quiz quiz = null;
            try {
                quiz = GetQuizById(quizId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            quizzes.add(quiz);
        }
        return quizzes;
    }

    private static QuizHistory parseQuizHistory(ResultSet rs) throws SQLException {
        int quiz_id = rs.getInt(1);
        String username = rs.getString(2);
        int result_id = rs.getInt(3);
        int score = rs.getInt(4);
        double percent = rs.getDouble(5);
        int quizDuration = rs.getInt(6);
        Timestamp timestamp = rs.getTimestamp(7);
        return new QuizHistory(quiz_id, username, result_id
                , score, percent, quizDuration, timestamp);
    }

    public static List<QuizHistory> getQuizHistory(int quizId) throws SQLException {
        List<QuizHistory> results = new ArrayList<>();
        String query = "SELECT q.quiz_id, u.Username, q.result_id, q.score, q.percent, q.quiz_duration, q.finish_time FROM tank_database.quiz_results q " +
                "left join tank_database.users u\n" +
                "on q.user_id = u.id\n" +
                "where q.quiz_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, quizId);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            QuizHistory result = parseQuizHistory(rs);
            results.add(result);
        }
        return results;
    }

    public static void clearQuizHistory(int quizId) throws SQLException {
        String query = "DELETE FROM quiz_results WHERE quiz_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, quizId);
        statement.execute();
    }

    public static int getNumberOfQuizzesTaken() throws SQLException {
        String query = "Select Count(distinct (quiz_id)) as num_quizzes from quiz_results";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int numberOfUsers = resultSet.getInt("num_quizzes");
        return numberOfUsers;
    }

    public static List<AnnouncementUserModel> getAnnouncements() throws SQLException {
        List<AnnouncementUserModel> results = new ArrayList<>();
        String query = "SELECT u.username, a.announcement\n" +
                "from announcements a\n" +
                "join users u\n" +
                "on a.admin_id = u.id";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            AnnouncementUserModel result = parseAnnouncementUserModel(rs);
            results.add(result);
        }
        return results;
    }

    private static AnnouncementUserModel parseAnnouncementUserModel(ResultSet rs) throws SQLException {
        String username = rs.getString(1);
        String announcement = rs.getString(2);
        return new AnnouncementUserModel(username, announcement);
    }


}
