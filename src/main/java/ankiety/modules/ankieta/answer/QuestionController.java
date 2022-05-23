package ankiety.modules.ankieta.answer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/question")
public class QuestionController {
    public final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/view/{id}")
    public String getQuestion(@PathVariable Long id, Model model, Principal principal) {
        Question question = questionService.getQuestionById(id);
        model.addAttribute("question", question);
        return "question-view";
    }

}
