package com.chocoyo.labs.app.demo.services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Log.d("foo", "From: " + remoteMessage.getFrom());
        Log.d("foo", "Notification Message Body: " + remoteMessage.getNotification().getBody());
        Log.i("foo", remoteMessage.getData().toString());
    }
}