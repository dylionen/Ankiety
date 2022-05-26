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
    public final SingleAnswerService singleAnswerService;

    public QuestionController(QuestionService questionService, SingleAnswerService singleAnswerService) {
        this.questionService = questionService;
        this.singleAnswerService = singleAnswerService;
    }

    @GetMapping("/edit/{id}")
    public String getQuestion(@PathVariable Long id, Model model, Principal principal) {
        Question question = questionService.getQuestionById(id);
        QuestionDTO questionDTO = new QuestionDTO(question);
        model.addAttribute("questionDTO", questionDTO);
        model.addAttribute("answers", singleAnswerService.getSingleAnswersByQuestion(question));
        return "question-edit";
    }

    @Transactional
    @PostMapping("/edit/{id}")
    public String postQuestion(@ModelAttribute QuestionDTO questionDTO, @PathVariable Long id, Model model, Principal principal,
                               @RequestParam(value = "action", required = true) String action) {
        //
        Question question = questionService.getQuestionById(id);

        if (action.equals("new")) {
            SingleAnswer answer = new SingleAnswer();
            Set<SingleAnswer> answerSet = question.getAnswers() == null ? new HashSet<>() : question.getAnswers();
            answerSet.add(answer);
            question.setAnswers(answerSet);
        }
        question.setMultipleChoice(questionDTO.getMultipleChoice());
        question.setOpenQuestion(questionDTO.getOpenQuestion());
        question.setQuery(questionDTO.getQuery());

        questionService.saveQuestion(question);
        model.addAttribute("answers", singleAnswerService.getSingleAnswersByQuestion(question));

        if (action.equals("new")) {
            return "question-edit";
        } else {
            return "redirect:/new/list/" + question.getHeader().getId();
        }
    }

    @GetMapping("/edit/single/{id}")
    public String getSingleEditView(@PathVariable Long id, Model model, Principal principal) {
        SingleAnswer singleAnswer = questionService.getSingleAnswerById(id);
        model.addAttribute("singleAnswer", singleAnswer);
        return "single-answer-edit";
    }

    @GetMapping("/edit/single/{id}/delete")
    public String deleteSingleEditView(@PathVariable Long id, Model model, Principal principal) {
        SingleAnswer singleAnswer = questionService.getSingleAnswerById(id);
        Long questionId = singleAnswer.getQuestion().getId();
        questionService.deteleSingleAnswer(singleAnswer);
        //model.addAttribute("singleAnswer",singleAnswer);
        return "redirect:/question/edit/" + questionId;
    }

    @PostMapping("/edit/single/{id}")
    public String postSingleEditView(@ModelAttribute SingleAnswer singleAnswer, @PathVariable Long id, Model model, Principal principal) {

        SingleAnswer singleAnswerEdit = questionService.getSingleAnswerById(id);
        singleAnswerEdit.setValue(singleAnswer.getValue());
        questionService.saveSingleAnswer(singleAnswerEdit);
        //model.addAttribute("singleAnswer",singleAnswer);
        return "redirect:/question/edit/" + singleAnswerEdit.getQuestion().getId();
    }

}
