package com.hervmo.manager.services.imp;

import com.hervmo.manager.exception.UserValidationException;
import com.hervmo.manager.models.entities.Role;
import com.hervmo.manager.models.entities.VerificationToken;
import com.hervmo.manager.models.entities.models.UsersModel;
import com.hervmo.manager.models.entities.Users;
import com.hervmo.manager.models.repositories.RoleRepository;
import com.hervmo.manager.models.repositories.UsersRepository;
import com.hervmo.manager.models.repositories.VerificationTokenRepository;
import com.hervmo.manager.services.UserValidation;
import com.hervmo.manager.services.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@Slf4j
public class UsersServiceImp implements UsersService {


    @Autowired
    public UserValidation userValidation;
    @Autowired
    protected RoleRepository roleRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    //@Todo add logging Info to help us for debug if some error occur

    @Override
    public Optional<Users> registerUser(UsersModel userModel) throws UserValidationException {
        Optional<UsersModel> userModelOptional = Optional.ofNullable(userModel);
        if (userModelOptional.isPresent()) {

            if (userValidation.emailValidation(userModelOptional.get().getEmail())) {
                throw new UserValidationException("Email was not comform ");
            }
            if (userValidation.requiredField(userModelOptional.get().getFirstname())) {
                throw new UserValidationException("Firstname is required ");
            }
            if (userValidation.requiredField(userModelOptional.get().getLastname())) {
                throw new UserValidationException("lastname is required");
            }
            if (userValidation.passwordPattern(userModelOptional.get().getPassword())) {
                throw new UserValidationException("please make sure your  password contain at least " +
                        userValidation.passwordPatternValue());
            }
            Optional<Users> userFindByEmail = usersRepository.findByEmail(userModel.getEmail());
            if (userFindByEmail.isPresent()) {
                throw new UserValidationException("email :  " + userFindByEmail.get().getEmail() + "already added");
            }
            return Optional.of(usersRepository.save(saveUser(null, userModelOptional.get())));
        }
        throw new UserValidationException(" you send a empty request but user is required ");
    }

    @Override
    public void saveVerificationTokenForUser(String token, Users user) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);

    }

    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Override
    public void validateVerificationToken(String token) throws UserValidationException {
        Optional<VerificationToken> vf = Optional.ofNullable(verificationTokenRepository.findByToken(token));
        if (vf.isPresent()) {
            Users user = vf.get().getUser();
            Calendar cal = Calendar.getInstance();
            if (vf.get().getExpirationTime().getTime() - cal.getTime().getTime() <= 0) {

                throw new UserValidationException("this token is expired please renew your registration");

            }
            if (!user.getActive()) {
                user.setActive(true);
                //return
                usersRepository.save(user);
            }
            throw new UserValidationException("User is already activate ");
        }
        throw new UserValidationException("this token is not valid please make a new registration to get a valid Token");
    }

    @Override
    public Users saveUser(Users user, UsersModel usersModel) {
        Users u = new Users();
        if (usersModel != null) {
            Role role = getOrCreateRole("USER");
            u.setFirstname(usersModel.getFirstname());
            u.setEmail(usersModel.getEmail());
            u.setLastname(usersModel.getLastname());
            u.setPassword(passwordEncoder.encode(usersModel.getPassword()));
            u.getRoles().add(role);
            u.setCreatedAt();
            u.setUpdateAt();
        } else {
            u = user;
        }
        return usersRepository.save(u);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getOrCreateRole(String roleName) {
        Optional<Role> existingRole = roleRepository.findByName(roleName);
        if (existingRole.isEmpty()) {
            existingRole = Optional.of(new Role());
            existingRole.get().setName(roleName);
        }
        return saveRole(existingRole.get());
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        Users user = getUser(0, email);
        Role role = getOrCreateRole(roleName);
        if (user != null) {
            user.getRoles().add(role);
        }
    }

    @Override
    public Users getUser(long id, String email) {
        Optional<Users> u = id == 0 ? usersRepository.findByEmail(email) : usersRepository.findById(id);
        return u.orElse(null);
    }

    @Override
    public List<Users> getUsers() {
        return usersRepository.findAll();
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findByActive(true).orElse(new ArrayList<>());
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users u = getUser(0, email);
        if (u == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if (!u.getActive()) {
            throw new UsernameNotFoundException("User is not active");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        u.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(u.getEmail(), u.getPassword(), authorities);
    }

    public UserValidation getUserValidation() {
        return userValidation;
    }
}
