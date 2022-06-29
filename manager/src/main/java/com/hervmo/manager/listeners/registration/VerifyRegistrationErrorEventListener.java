package com.hervmo.manager.listeners.registration;


import com.hervmo.manager.events.registration.VerifyRegistrationErrorEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class VerifyRegistrationErrorEventListener implements ApplicationListener<VerifyRegistrationErrorEvent> {


    @Override
    public void onApplicationEvent(VerifyRegistrationErrorEvent event) {

        String message = event.getMessage();
        // @Todo send the verification Email
        log.info(" some error occured: {}", message);
    }
}
