package com.hervmo.manager;

import com.hervmo.manager.models.entities.Users;
import com.hervmo.manager.models.entities.models.UsersModel;
import com.hervmo.manager.services.UsersService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ManagerApplication {

    public static void main(String[] args) {

        SpringApplication.run(ManagerApplication.class, args);


    }

    @Bean
    CommandLineRunner run(UsersService us) {
        UsersModel u = new UsersModel();
        u.setEmail("moriatatdja@yahoo.fr");
        u.setFirstname("moria");
        u.setLastname("Tatdja");
        u.setPassword("Mignon88..@@");
        return args -> {
            Users user = us.saveUser(null, u);
            user.setActive(true);
            us.saveUser(user, null);
        };
    }
}
