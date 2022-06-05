package ankiety.modules.users;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;


@Service
public class UserService {
    final UserRepository userRepository;
    final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void createNewUser(User user) {
        if(!existsByUserName(user.getUserName())) {
            userRepository.save(user);
        } else {
            System.out.println("User: " + user.getUserName() + " exists.");
        }
    }

    @Transactional
    public void createNewUser(UserRegistrationDTO dto) {
        Set<Role> roleSet = new HashSet<>();
        Optional<Role> role = roleRepository.findByRoleName("USER");
        role.ifPresentOrElse(
                r -> roleSet.add(r),
                () -> {
                    throw new NoSuchElementException("Role not found");
                }
        );
        User user = new User(dto, roleSet);
        userRepository.save(user);
    }

    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    public boolean existsByEmail(String emailAddress) {
        return userRepository.existsByMailAddress(emailAddress);
    }

    public Long getUserIdByUserName(String userName) {
        return userRepository.getUserByUserName(userName).getId();
    }

    public User getUserByUserName(String userName) {
        return userRepository.getUserByUserName(userName);
    }

}
