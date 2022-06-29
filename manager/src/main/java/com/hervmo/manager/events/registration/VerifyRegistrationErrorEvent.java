package com.hervmo.manager.events.registration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class VerifyRegistrationErrorEvent extends ApplicationEvent {
    private String message;

    public VerifyRegistrationErrorEvent(String message) {
        super(message);
        this.message = message;
    }
}
