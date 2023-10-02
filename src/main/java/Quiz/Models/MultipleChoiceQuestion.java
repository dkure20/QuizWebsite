package Quiz.Models;

import Quiz.Models.enums.questionType;

import java.util.List;

public class MultipleChoiceQuestion extends SingleAnswerQuestion{

    List<MultipleChoiceAnswer> possibleAnswers;
    public MultipleChoiceQuestion() {
    }

    public MultipleChoiceQuestion(int quizId) {
        super(quizId);
    }

    public MultipleChoiceQuestion(int id, String content, int quizId, Answer answer) {
        super(id, content, quizId, answer);
    }

    public MultipleChoiceQuestion(int id, String content, int quizId, int type) {
        super(id, content, quizId, type);
    }

    public MultipleChoiceQuestion(int id, String content, int quizId) {
        super(id, content, quizId);
    }

    public void AddAnswers(List<MultipleChoiceAnswer> ans){
        possibleAnswers = ans;
    }

    public List<MultipleChoiceAnswer> getPossibleAnswers(){
        return possibleAnswers;
    }

    @Override
    public questionType getType() {
        return questionType.Multiple_Choice;
    }

    public String getQuestionDisplayHtml(){
        StringBuilder strBuild = new StringBuilder();
        strBuild.append("<class=\"question\">");
        strBuild.append(this.getContent());
        strBuild.append("<br>");
        return strBuild.toString();
    }

    public String displayFieldsHtml(int index){
        StringBuilder htmlBuilder = new StringBuilder();
        for (MultipleChoiceAnswer ans : possibleAnswers){
            htmlBuilder.append("<label><input type=\"radio\" name=\"response");
            htmlBuilder.append(index);
            htmlBuilder.append("\" value=\"");
            htmlBuilder.append("index");
            htmlBuilder.append("\" required>");
            htmlBuilder.append(ans.getContent());
            htmlBuilder.append("</label>");
        }
        return htmlBuilder.toString();
    }

}
