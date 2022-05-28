package ankiety.modules.ankieta.header;

import ankiety.modules.ankieta.answer.Question;
import ankiety.modules.ankieta.link.Link;
import ankiety.modules.users.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HeaderService {

    private final HeaderRepository headerRepository;

    private final UserService userService;


    public HeaderService(HeaderRepository headerRepository, UserService userService) {
        this.headerRepository = headerRepository;
        this.userService = userService;
    }

    @Transactional
    public void createNewHeader(Header header, Principal principal) {
        if (header.getId() != null) {
            throw new RuntimeException("Nie można edytować z tej pozycji");
        }
        header.setUser(userService.getUserByUserName(principal.getName()));
        header.setCreateMode(true);
        header.setAddedDate(new Timestamp(System.currentTimeMillis()));
        header.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        headerRepository.save(header);
    }

    public List<Header> getAllUserHeaders(Principal principal) {
        return headerRepository.getHeadersByUser(userService.getUserByUserName(principal.getName()));
    }

    public Header getHeaderById(Long id) {
        return headerRepository.getById(id);
    }

    @Transactional
    public Header newQuestion(Long id) {
        Header header = headerRepository.getById(id);
        Question question = new Question();
        Set<Question> questions = header.getQuestions() == null ? new HashSet<>() : header.getQuestions();
        questions.add(question);
        headerRepository.save(header);
        return header;

    }

    @Transactional
    public void closeHeader(Long id) {
        Header header = headerRepository.getById(id);
        header.setCreateMode(false);
        headerRepository.save(header);
    }

    @Transactional
    public void newLink(Long id) {
        Header header = headerRepository.getById(id);
        Link link = new Link();
        Set<Link> links = header.getLinks() == null ? new HashSet<>() : header.getLinks();
        links.add(link);
        headerRepository.save(header);
    }

}
