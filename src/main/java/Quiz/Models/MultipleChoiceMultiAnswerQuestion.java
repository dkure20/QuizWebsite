package Quiz.Models;

import Quiz.Models.enums.questionType;

import java.util.List;

public class MultipleChoiceMultiAnswerQuestion extends MultipleAnswerQuestion{
    public MultipleChoiceMultiAnswerQuestion(int quizId) {
        super(quizId);
    }

    public MultipleChoiceMultiAnswerQuestion(int id, String content, int quizId, List<Answer> answers) {
        super(id, content, quizId, answers);
    }

    public MultipleChoiceMultiAnswerQuestion(int id, String content, int quizId) {
        super(id, content, quizId);
    }

    @Override
    public questionType getType() {
        return null;
    }


    @Override
    public String getCreatingAnswersHtml() {
        StringBuilder html = new StringBuilder();
        html.append("<form action='temp' method='post'>");
        html.append("<div id='inputContainer'>");
        html.append("<div>");
        html.append("<input type='text' name='input[]' placeholder='Enter your input'>");
        html.append("<select name='correct[]'>");
        html.append("<option value='true'>True</option>");
        html.append("<option value='false'>False</option>");
        html.append("</select>");
        html.append("</div>");
        html.append("</div>");
        html.append("<button type='button' onclick='addInput()'>Add Input</button>");
        html.append("<input type='submit' value='Submit'>");
        html.append("</form>");
        html.append("<script>");
        html.append("function addInput() {");
        html.append("var inputContainer = document.getElementById('inputContainer');");
        html.append("var newInputDiv = document.createElement('div');");
        html.append("var newInput = document.createElement('input');");
        html.append("var newSelect = document.createElement('select');");
        html.append("var optionTrue = document.createElement('option');");
        html.append("var optionFalse = document.createElement('option');");
        html.append("newInput.setAttribute('type', 'text');");
        html.append("newInput.setAttribute('name', 'input[]');");
        html.append("newInput.setAttribute('placeholder', 'Enter your input');");
        html.append("newSelect.setAttribute('name', 'correct[]');");
        html.append("optionTrue.setAttribute('value', 'true');");
        html.append("optionTrue.textContent = 'True';");
        html.append("optionFalse.setAttribute('value', 'false');");
        html.append("optionFalse.textContent = 'False';");
        html.append("newSelect.appendChild(optionTrue);");
        html.append("newSelect.appendChild(optionFalse);");
        html.append("newInputDiv.appendChild(newInput);");
        html.append("newInputDiv.appendChild(newSelect);");
        html.append("inputContainer.appendChild(newInputDiv);");
        html.append("}");
        html.append("</script>");

        return html.toString();
    }
}
