package ankiety.modules.raport;

import ankiety.modules.ankieta.answer.Question;
import ankiety.modules.ankieta.header.Header;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Report {
    private final List<ReportQuestion> reportQuestionList = new ArrayList<>();
    private Integer respondentNumber;

    public Report(Header header) {
        respondentNumber = 0;
        for (Question question : header.getQuestions()) {
            reportQuestionList.add(new ReportQuestion(question, header));
        }
        header.getLinks().stream().filter(link -> link.getAnswer() != null).forEach(link ->
                respondentNumber++
        );
    }
}
