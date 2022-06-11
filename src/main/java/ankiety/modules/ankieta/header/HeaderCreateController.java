package ankiety.modules.ankieta.header;

import ankiety.configurations.SecurityConfiguration;
import ankiety.modules.ankieta.link.Link;
import ankiety.modules.ankieta.link.LinkService;
import ankiety.modules.raport.Report;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/new")
public class HeaderCreateController {

    private final HeaderService headerService;
    private final LinkService linkService;

    public HeaderCreateController(HeaderService headerService, LinkService linkService) {
        this.headerService = headerService;
        this.linkService = linkService;
    }

    @GetMapping
    public String newForm(Model model) {
        Header header = new Header();
        model.addAttribute("header", header);
        return "new-form";
    }

    @PostMapping("/add")
    public String saveForm(@ModelAttribute Header header, Principal principal) {
        headerService.createNewHeader(header, principal);
        return "redirect:/new/list";
    }

    @GetMapping("/list")
    public String myFormsList(Model model, Principal principal) {
        model.addAttribute("headers", headerService.getAllUserHeaders(principal));
        return "form-list";
    }

    @GetMapping("/list/{id}")
    public String myForm(@PathVariable Long id, @RequestParam(required = false, defaultValue = "false") Boolean setClosed,
                         @RequestParam(required = false, defaultValue = "false") Boolean newLink,
                         Model model) {
        if (setClosed) {
            headerService.closeHeader(id);
        }
        if (newLink) {
            headerService.newLink(id);
        }
        Header header = headerService.getHeaderById(id);
        Boolean questionnairesAreCompleted = false;
        if (!header.getCreateMode()) {
            if (header.getLinks().stream().filter(link -> link.getAnswer() == null).collect(Collectors.toSet()).size() == 0) {
                questionnairesAreCompleted = true;
            }
        }


        model.addAttribute("questionnairesAreCompleted", questionnairesAreCompleted);
        model.addAttribute("header", header);
        model.addAttribute("pageAddress", SecurityConfiguration.PAGE_URL);
        return "single-form";
    }

    @GetMapping("/list/{id}/report")
    public String getReport(@PathVariable Long id, Model model, Principal principal) {
        Header header = headerService.getHeaderById(id);
        if (!header.getUser().getUserName().equals(principal.getName())) {
            return "error";
        }
        Report report = new Report(header);
        model.addAttribute("report", report);
        return "report";
    }

    @GetMapping("/list/{id}/delete/{key}")
    public String deleteKey(@PathVariable Long id, @PathVariable String key, Principal principal) {
        Link link = linkService.getLinkByKey(key);
        if (link.getHeader().getUser().getUserName().equals(principal.getName())) {
            linkService.linkRepository.delete(link);
        } else {
            System.out.println(principal.getName() + " nie jest wlasicielem key: " + key);
        }
        return "redirect:/new/list/{id}";
    }

    @GetMapping("/list/{id}/question")
    public String myFormNewQuestion(@PathVariable Long id, Model model) {
        Header header = headerService.newQuestion(id);
        model.addAttribute("header", header);
        return "redirect:/new/list/{id}";
    }
}
