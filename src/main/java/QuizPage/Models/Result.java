package QuizPage.Models;

import java.sql.Timestamp;

public class Result {
    private int id;
    private int userId;
    private int quizId;
    private int score;
    private int maxScore;
    private int percent;
    private int duration;
    private Timestamp finishTime;

    public Result(int id, int userId, int quizId, int score, int percent, int duration, Timestamp finishTime){
        this.id = id;
        this.userId = userId;
        this.quizId = quizId;
        this.score = score;
        this.maxScore = maxScore;
        this.percent = percent;
        this.duration = duration;
        this.finishTime = finishTime;
    }

    public Result(int userId, int quizId, int score, int percent, int duration, Timestamp finishTime){
        this.userId = userId;
        this.quizId = quizId;
        this.score = score;
        this.percent = percent;
        this.duration = duration;
        this.finishTime = finishTime;
    }

    public  int getId(){ return id; }
    public int getUserId() { return userId; }

    public int getQuizId() { return quizId; }

    public int getScore() { return score; }


    public int getPercent() { return percent; }

    public int getDuration() { return duration; }

    public Timestamp getFinishTime() { return finishTime; }
}
