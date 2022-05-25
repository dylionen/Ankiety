package ankiety.modules.ankieta.answer;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Comparator;
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
        Question question =questionService.getQuestionById(id);
        QuestionDTO questionDTO = new QuestionDTO(question);
        model.addAttribute("questionDTO", questionDTO);
        System.out.println("Jest: " + question.getAnswers().size());
        model.addAttribute("answers", question.getAnswers());
        return "question-edit";
    }
    @Transactional
    @PostMapping("/edit/{id}")
    public String postQuestion(@ModelAttribute QuestionDTO questionDTO, @PathVariable Long id, Model model, Principal principal,
                               @RequestParam(value = "action", required = true) String action) {
        //
        Question question = questionService.getQuestionById(id);

        if(action.equals("new")){
            SingleAnswer answer = new SingleAnswer();
            Set<SingleAnswer> answerSet = question.getAnswers() == null ? new HashSet<>() : question.getAnswers();
            answerSet.add(answer);
            question.setAnswers(answerSet);
        }
        question.setMultipleChoice(questionDTO.getMultipleChoice());
        question.setOpenQuestion(questionDTO.getOpenQuestion());
        question.setQuery(questionDTO.getQuery());

        questionService.saveQuestion(question);
        model.addAttribute("answers", question.getAnswers());
        System.out.println(question);

        return "question-edit";
    }

}
