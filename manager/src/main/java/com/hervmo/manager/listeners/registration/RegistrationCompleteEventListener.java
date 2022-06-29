package com.hervmo.manager.listeners.registration;

import com.hervmo.manager.events.registration.RegistrationCompleteEvent;
import com.hervmo.manager.models.entities.Users;
import com.hervmo.manager.services.MailService;
import com.hervmo.manager.services.UsersService;

import com.hervmo.manager.utils.Constants;
import com.hervmo.manager.utils.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UsersService usersService;
    @Autowired
    private MailService mailService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent registrationCompleteEvent) {
        Users user = registrationCompleteEvent.getUser();
        String token = UUID.randomUUID().toString();
        usersService.saveVerificationTokenForUser(token, user);

        String url = registrationCompleteEvent.getApplicationUrl() + "/" + Constants.REGISTRATION_ROUTE_NAME + "?"
                + Constants.TOKEN_REQUEST_PARAMETER + "=" + token;

        // @Todo send the verification Email
        System.out.println("click the link to verify your account: " + url);


        Mail mail = new Mail();
        mail.setMailTo(user.getEmail());
        mail.setMailFrom("rktermin2@gmail.com");
        mail.setMailSubject("Verify Email");
        mail.setMailContent("Thank you for registration please follow the <a href='" + url
                + "'>link</a> to complete your registration");
        mailService.sendEmail(mail);
    }


}
