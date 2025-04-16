package com.medixpress.notification;

public interface ISmsSender {
    void sendSms(String phoneNumber, String message);
}
