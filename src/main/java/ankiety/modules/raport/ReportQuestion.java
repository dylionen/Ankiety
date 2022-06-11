package ankiety.modules.raport;

import ankiety.modules.ankieta.answer.Question;
import ankiety.modules.ankieta.header.Header;
import lombok.Getter;

import java.util.*;

@Getter
public class ReportQuestion {

    private List<String> counterAnswers;
    private Boolean openQuestion;
    private Boolean multipleChoice;
    private Integer respondentsNumber;
    private Question question;

    private List<ReportSingleAnswers> reportSingleAnswersList;

    public ReportQuestion(Question question, Header header) {
        this.question = question;
        respondentsNumber = 0;
        reportSingleAnswersList = new ArrayList<>();
        this.openQuestion = question.getOpenQuestion();
        this.multipleChoice = question.getMultipleChoice();
        counterAnswers = new ArrayList<>();
        if (openQuestion) {
            header.getLinks().forEach(link -> {
                if (link.getAnswer() != null) {
                    link.getAnswer().getQuestions().forEach(singleQuestion -> {
                        if (Objects.equals(singleQuestion.getQuestion().getId(), question.getId())) {
                            respondentsNumber++;
                            counterAnswers.add(singleQuestion.getOpenQuestionAnsewer());
                        }
                    });
                }
            });

        } else {
            header.getLinks().forEach(link -> {
                if (link.getAnswer() != null) {
                    link.getAnswer().getQuestions().forEach(singleQuestion -> {
                        if (Objects.equals(singleQuestion.getQuestion().getId(), question.getId())) {
                            respondentsNumber++;
                            singleQuestion.getAnswers().forEach(singleAnswer -> counterAnswers.add(singleAnswer.getValue()));
                        }
                    });
                }
            });
            Set<String> uniqueCounterAnswers = new HashSet<>(counterAnswers);
            uniqueCounterAnswers.stream().filter(Objects::nonNull).forEach(s -> {
                        Integer counter = counterAnswers.stream().filter(s1 -> s1.equals(s)).toList().size();
                        Double percentage =  ((double)counter / respondentsNumber);

                        reportSingleAnswersList.add(new ReportSingleAnswers(s, counter, (int) (percentage*100)));
                    }
            );
        }
    }
}

