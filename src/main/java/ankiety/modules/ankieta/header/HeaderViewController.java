package ankiety.modules.ankieta.header;

import ankiety.modules.ankieta.link.LinkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
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
        if (optionalHeader.isEmpty()) {
            return "error";
        }
        Header header = optionalHeader.get();
        model.addAttribute("key", key);
        model.addAttribute("header", header);
        return "ankieta-view";
    }

    @PostMapping("/{key}/send")
    public String sendTheSurvey(@PathVariable String key, @RequestParam Map<String, String> headers, Model model, Principal
            principal) {
        for (String str : headers.keySet()) {
            System.out.println(str +" : : : " +  headers.get(str));
        }


        /*
        Optional<Header> optionalHeader = linkService.getHeaderByKey(key);
        if(optionalHeader.isEmpty()){
            return "error";
        }
        Header header = optionalHeader.get();
        model.addAttribute("header", header);*/
        return "index";
    }

}
