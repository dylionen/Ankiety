package ankiety.modules.ankieta.answer;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/question")
public class QuestionController {
    public final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/edit/{id}")
    public String getQuestion(@PathVariable Long id, Model model, Principal principal) {
        Question question = questionService.getQuestionById(id);
        model.addAttribute("question", question);
        return "question-edit";
    }
    @Transactional
    @PostMapping("/edit/{id}")
    public String postQuestion(@ModelAttribute Question question, @PathVariable Long id, Model model, Principal principal,
                               @RequestParam(value = "action", required = true) String action) {
        //model.addAttribute("question", question);
        System.out.println(action);
        if(action.equals("new")){
            SingleAnswer answer = new SingleAnswer();
            Set<SingleAnswer> answerSet = question.getAnswers() == null ? new HashSet<>() : question.getAnswers();
            answerSet.add(answer);
            question.setAnswers(answerSet);
            questionService.saveQuestion(question);
        } else {
            questionService.saveQuestion(question);
        }


        System.out.println(question);
        return "question-edit";
    }

}
