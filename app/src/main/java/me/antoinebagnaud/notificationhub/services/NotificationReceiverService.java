package me.antoinebagnaud.notificationhub.services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import me.antoinebagnaud.notificationhub.models.NotificationActionModel;
import me.antoinebagnaud.notificationhub.models.NotificationModel;
import timber.log.Timber;

public class NotificationReceiverService extends NotificationListenerService {

    @Override
    public void onCreate() {
    }

    @SuppressLint("LogNotTimber")
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if(sbn.isOngoing()) {
            return;
        }
        Notification notification = sbn.getNotification();
        Bundle extras = notification.extras;
        NotificationModel amoi = new NotificationModel(
                sbn.getPackageName(),
                extras.getString(notification.EXTRA_TITLE),
                extras.getCharSequence(notification.EXTRA_TEXT).toString(),
                notification.color,
                notification.actions
        );


        if (notification.actions.length > 0) {
            for (Notification.Action a: notification.actions) {
                Timber.d(String.valueOf(new NotificationActionModel(a)));
            }
        }

        if ("msg".equals(notification.category)) {
            Timber.d(amoi.toString());
            EventBus.getDefault().post(amoi);
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Timber.d(sbn.getNotification().toString());
    }

}
