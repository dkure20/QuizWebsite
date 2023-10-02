package Quiz.Models;

import Quiz.Models.enums.questionType;

import java.util.List;

public class QuestionResponseQuestion extends MultipleAnswerQuestion{
    public QuestionResponseQuestion(){
        super();
    }
    public QuestionResponseQuestion(int quizId) {
        super(quizId);
    }

    public QuestionResponseQuestion(int id, String content, int quizId, List<Answer> answers) {
        super(id, content, quizId, answers);
    }

    public QuestionResponseQuestion(int id, String content, int quizId) {
        super(id, content, quizId);
    }

    public QuestionResponseQuestion(int id, String content, int quizId, int type) {
        super(id, content, quizId, type);
    }
    @Override
    public questionType getType() {
        return questionType.Question_Response;
    }

    public String getQuestionDisplayHtml(){
        StringBuilder strBuild = new StringBuilder();
        strBuild.append("<class=\"question\">");
        strBuild.append(this.getContent());
        strBuild.append("<br>");
        return strBuild.toString();
    }
}
