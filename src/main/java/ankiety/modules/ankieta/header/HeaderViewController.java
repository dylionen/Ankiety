package ankiety.modules.ankieta.header;

import ankiety.modules.ankieta.link.LinkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;


@Controller
@RequestMapping("/ankieta")
public class HeaderViewController {

    private final HeaderService headerService;
    private final LinkService linkService;

    public HeaderViewController(HeaderService headerService, LinkService linkService) {
        this.headerService = headerService;
        this.linkService = linkService;
    }


    @GetMapping("/{key}")
    public String completeTheSurvey(@PathVariable String key, Model model, Principal
            principal) {
        Optional<Header> optionalHeader = linkService.getHeaderByKey(key);

        Header header = optionalHeader.get();
        model.addAttribute("header", header);
        return "index";
    }


}
// TODO: 28.05.2022 obsłużyć optional
