package ankiety.modules.ankieta.header;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/new")
public class HeaderController {

    private final HeaderService headerService;

    public HeaderController(HeaderService headerService) {
        this.headerService = headerService;
    }

    @GetMapping
    public String newForm(Model model, Principal principal) {
        Header header = new Header();
        model.addAttribute("header", header);
        return "new-form";
    }

    @PostMapping("/add")
    public String saveForm(@ModelAttribute Header header, Model model, Principal principal) {
        headerService.createNewHeader(header, principal);
        return "redirect:/new/list";
    }

    @GetMapping("/list")
    public String myFormsList(Model model, Principal principal) {
        model.addAttribute("headers", headerService.getAllUserHeaders(principal));
        return "form-list";
    }



    @GetMapping("/list/{id}")
    public String myForm(@PathVariable Long id,@RequestParam(required = false, defaultValue = "false") Boolean setClosed,
                         @RequestParam(required = false, defaultValue = "false") Boolean newLink,
                         Model model, Principal principal) {
        System.out.println(setClosed);
        if(setClosed){
            headerService.closeHeader(id);
        }
        if(newLink){
            headerService.newLink(id);
        }
        Header header = headerService.getHeaderById(id);
        model.addAttribute("header", header);
        return "single-form";
    }





    @GetMapping("/list/{id}/question")
    public String myFormNewQuestion(@PathVariable Long id, Model model, Principal principal) {
        Header header = headerService.newQuestion(id);
        model.addAttribute("header", header);
        return "redirect:/new/list/{id}";
    }




}
