package ankiety.modules.ankieta.header;

import ankiety.modules.ankieta.answer.Question;
import ankiety.modules.ankieta.answer.SingleAnswer;
import ankiety.modules.ankieta.link.Link;
import ankiety.modules.ankieta.link.LinkService;
import ankiety.modules.ankieta.userAnswer.Answer;
import ankiety.modules.ankieta.userAnswer.SingleQuestion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Controller
@RequestMapping("/ankieta")
public class HeaderViewController {


    private final LinkService linkService;

    public HeaderViewController(LinkService linkService) {

        this.linkService = linkService;
    }


    @GetMapping("/{key}")
    public String completeTheSurvey(@PathVariable String key, Model model) {
        Link link = linkService.getLinkByKey(key);
        if (link == null) {
            return "error";
        }

        model.addAttribute("key", key);
        model.addAttribute("link", link);
        return "ankieta-view";
    }

    @PostMapping("/{key}/send")
    public String sendTheSurvey(@PathVariable String key, @RequestParam Map<String, String> headers) {
        //   for (String str : headers.keySet()) {
        //      System.out.println(str + " : : : " + headers.get(str));
        //  }

        Link link = linkService.getLinkByKey(key);
        if (link.getAnswer() != null) {
            return "error";
        }

        Answer answer = new Answer();
        answer.setLink(link);

        List<Question> questions = link.getHeader().getQuestions().stream().toList();

        Set<SingleQuestion> singleQuestions = new HashSet<>();// lista główna
        for (int i = 0; i < questions.size(); i++) {
            SingleQuestion question = new SingleQuestion();
            question.setQuestion(questions.get(i));

            if (questions.get(i).getOpenQuestion()) { // jest otwarte pytanie
                question.setOpenQuestionAnsewer(headers.get("question[" + (i + 1) + "]"));
            } else if (questions.get(i).getMultipleChoice()) {
                List<SingleAnswer> answers = questions.get(i).getAnswers().stream().toList();
                Set<SingleAnswer> correctAnswers = new HashSet<>();
                for (int j = 0; j < answers.size(); j++) {
                    if (headers.get("question[" + (i + 1) + "][" + (j + 1) + "]") != null) {
                        correctAnswers.add(answers.get(j));
                    }
                }

                question.setAnswers(correctAnswers);

            } else {
                List<SingleAnswer> answers = questions.get(i).getAnswers().stream().toList();
                Set<SingleAnswer> correctAnswers = new HashSet<>();
                for (SingleAnswer singleAnswer : answers) {
                    if (singleAnswer.getValue().equals(headers.get("question[" + (i + 1) + "]"))) {
                        correctAnswers.add(singleAnswer);
                    }
                }
                question.setAnswers(correctAnswers);
            }
            singleQuestions.add(question);
        }
        answer.setQuestions(singleQuestions);
        link.setAnswer(answer);
        linkService.linkRepository.save(link);
        return "redirect:/ankieta/" + key;
    }

}
