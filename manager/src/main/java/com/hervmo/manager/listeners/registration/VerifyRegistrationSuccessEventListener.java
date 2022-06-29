package com.hervmo.manager.listeners.registration;

import com.hervmo.manager.events.registration.VerifyRegistrationSuccessEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class VerifyRegistrationSuccessEventListener implements ApplicationListener<VerifyRegistrationSuccessEvent> {


    @Override
    public void onApplicationEvent(VerifyRegistrationSuccessEvent event) {

        String message = event.getMessage();

        // @Todo send the verification Email
        log.info("{}", message);
    }
}
