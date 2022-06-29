package com.hervmo.manager.utils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
public class Mail {


    private String mailFrom;

    private String mailTo;

    private String mailCc;

    private String mailBcc;

    private String mailSubject;

    private String mailContent;

    private String contentType;

    private List<Object> attachments;

    public Mail() {
        contentType = "text/plain";
    }

    public Date getMailSendDate() {
        return new Date();
    }
}
