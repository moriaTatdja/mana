package com.hervmo.manager.services;

import com.hervmo.manager.exception.UserValidationException;
import com.hervmo.manager.models.entities.Role;
import com.hervmo.manager.models.entities.Users;
import com.hervmo.manager.models.entities.models.UsersModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UsersService extends UserDetailsService {

    Optional<Users> registerUser(UsersModel userModel) throws UserValidationException;

    void saveVerificationTokenForUser(String token, Users user);

    void validateVerificationToken(String token) throws UserValidationException;

    Users saveUser(Users user, UsersModel usersModel);

    Role saveRole(Role role);

    Role getOrCreateRole(String roleName);

    void addRoleToUser(String email, String roleName);

    Users getUser(long id, String email);

    List<Users> getUsers();

    List<Role> getRoles();

    UserDetails loadUserByUsername(String email);

    UserValidation getUserValidation();


}
