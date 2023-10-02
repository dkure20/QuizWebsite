package Quiz.Models;

import Quiz.Models.enums.questionType;

import java.util.List;

public class FillInTheBlankQuestion extends MultipleAnswerQuestion{
    public FillInTheBlankQuestion() {
    }

    public FillInTheBlankQuestion(int quizId) {
        super(quizId);
    }

    public FillInTheBlankQuestion(int id, String content, int quizId, List<Answer> answers) {
        super(id, content, quizId, answers);
    }

    public FillInTheBlankQuestion(int id, String content, int quizId, int type) {
        super(id, content, quizId, type);
    }

    public FillInTheBlankQuestion(int id, String content, int quizId) {
        super(id, content, quizId);
    }

    @Override
    public questionType getType() {
        return questionType.Fill_In_The_Blank;
    }
    public String getQuestionDisplayHtml(){
        StringBuilder strBuild = new StringBuilder();
        strBuild.append("<class=\"question\">");
        strBuild.append(this.getContent());
        strBuild.append("<br>");
        return strBuild.toString();
    }
}
