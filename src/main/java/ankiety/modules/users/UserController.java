package ankiety.modules.users;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/")
    public String getHomePage(Authentication authentication, Model model) {
        if (authentication != null) {
            model.addAttribute(authentication);
        }

        /*
        //model.addAttribute("jobs",jobService.getAllJobServices());

        int currentPage = page.orElse(1);

        model.addAttribute("jobs", pages);
        model.addAttribute("pageSize", pageSize);

        int totalPages = pages.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
*/
        return "index";
    }

    @GetMapping("/user")
    public String userPanel(Model model, Principal principal) {
        //model.addAttribute("companies", companyDTOList);
        //model.addAttribute("jobs", jobDTOS);
        return "userpanel";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        UserRegistrationDTO user = new UserRegistrationDTO();
        model.addAttribute("user", user);
        return "signup";
    }

    @PostMapping("/process_register")
    public String registerConfirnation(@Valid @ModelAttribute("user") UserRegistrationDTO user, BindingResult bindingResult, Model model) {
        if (userService.existsByUserName(user.getUserName()))
            bindingResult.rejectValue("userName", null, "Username exists!!");

        if (userService.existsByEmail(user.getMailAddress()))
            bindingResult.rejectValue("mailAddress", null, "Email exists!!");

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "signup";

        } else {
            userService.createNewUser(user);
            return "index";
        }
    }

/*
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/foradmin")
    @ResponseBody
    public String someMethod() {
        return "<h1> Dla admina</h1>";
    }*/
}
