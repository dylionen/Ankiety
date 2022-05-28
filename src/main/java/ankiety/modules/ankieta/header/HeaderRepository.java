package ankiety.modules.ankieta.header;

import ankiety.modules.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeaderRepository extends JpaRepository<Header, Long> {

    public List<Header> getHeadersByUser(User user);

}
