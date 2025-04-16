package com.medixpress.notification.impl;

import com.medixpress.notification.ISmsSender;
import com.medixpress.notification.config.TwilioConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TwilioSmsSender implements ISmsSender {

    private final TwilioConfig twilioConfig;

    @Autowired
    public TwilioSmsSender(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;

        // Initialize Twilio client
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

    @Override
    public void sendSms(String toPhoneNumber, String messageBody) {
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(toPhoneNumber),
                new com.twilio.type.PhoneNumber(twilioConfig.getPhoneNumber()),
                messageBody
        ).create();

        System.out.println("✅ SMS sent! SID: " + message.getSid());
    }
}
