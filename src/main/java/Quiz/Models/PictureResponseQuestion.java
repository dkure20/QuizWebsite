package Quiz.Models;

import Quiz.Models.enums.questionType;

import java.util.List;

public class PictureResponseQuestion extends MultipleAnswerQuestion{
    public PictureResponseQuestion() {
    }

    public PictureResponseQuestion(int quizId) {
        super(quizId);
    }

    public PictureResponseQuestion(int id, String content, int quizId, List<Answer> answers) {
        super(id, content, quizId, answers);
    }

    public PictureResponseQuestion(int id, String content, int quizId, int type) {
        super(id, content, quizId, type);
    }

    public PictureResponseQuestion(int id, String content, int quizId) {
        super(id, content, quizId);
    }

    @Override
    public questionType getType(){
        return questionType.Picture_Response_Question;
    }
    @Override
    public String getCreatingDescriptionHtml() {
        String res = "<div class=\"question-description\">" +
                "<label class=\"question-label\" for='pictureUrl'>Picture URL:</label><br> " +
                "<textarea id='pictureUrl' name='pictureUrl' rows='1' cols='30'></textarea><br><br>" +
                "</div>";;
        return res;
    }

    public String getQuestionDisplayHtml(){
        //"<style=\"text-align: center;\"><img src=\"https://example.com/image.jpg\" alt=\"Image Preview\" style=\"max-width: 100%; border: 1px solid #ccc; border-radius: 4px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);\"></p>\n"
        StringBuilder strBuild = new StringBuilder();
        strBuild.append("<style=\"text-align: center;\"><img src=\"");
        strBuild.append(this.getContent());
        strBuild.append("\" alt=\"Image Preview\"");
        strBuild.append("style=\\\"max-width: 100%;");
        strBuild.append("border: 1px solid #ccc;");
        strBuild.append("border-radius: 4px;");
        strBuild.append("box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);");
        strBuild.append("\">");
        return strBuild.toString();
    }
}
