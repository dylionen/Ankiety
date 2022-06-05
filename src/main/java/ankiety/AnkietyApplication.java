package ankiety;

import ankiety.modules.users.Role;
import ankiety.modules.users.RoleController;
import ankiety.modules.users.User;
import ankiety.modules.users.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class AnkietyApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AnkietyApplication.class, args);
        UserService userService = context.getBean(UserService.class);
        RoleController roleController = context.getBean(RoleController.class);
        roleController.newRole(1L,"ADMIN","Administrator");
        roleController.newRole(1L,"USER","Użytkownik");


        Set<Role> roles = new HashSet<>();
        roles.add(roleController.getRole(1L));


        User admin = new User(1L,"admin","$2a$12$pqlgwxC7C9G490XeXCT0WuP8.1KAoTdY8taQtlbatFfe0VBKv/ipS",true,"ADMIN","admin","asd@2p.pl",roles);
        userService.createNewUser(admin);
    }

}
