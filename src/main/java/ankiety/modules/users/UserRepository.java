package ankiety.modules.users;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByUserName(String userName);

    boolean existsByMailAddress(String mailAddress);

    boolean existsByUserName(String userName);

    User getUserByUserName(String userName);

}
