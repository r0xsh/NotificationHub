package me.antoinebagnaud.notificationhub.models;

import android.app.RemoteInput;

public class NotificationRemoteInputModel {

    private String text;

    public NotificationRemoteInputModel(RemoteInput remoteInput) {
        this.text = remoteInput.getLabel().toString();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NotificationRemoteInputModel{");
        sb.append("text='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
