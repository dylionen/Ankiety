package ankiety.modules.ankieta.header;

import ankiety.modules.users.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;

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

}
