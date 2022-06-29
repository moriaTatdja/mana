package com.hervmo.manager.events.registration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class VerifyRegistrationSuccessEvent extends ApplicationEvent {
    private String message;

    public VerifyRegistrationSuccessEvent(String messsage) {
        super(messsage);
        this.message = messsage;
    }
}
