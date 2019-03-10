package me.antoinebagnaud.notificationhub.models;

import android.app.Notification;
import android.app.RemoteInput;

import java.util.ArrayList;
import java.util.List;

public class NotificationActionModel {

    private String title;

    private List<NotificationRemoteInputModel> remoteInputs = new ArrayList<>();

    public NotificationActionModel(Notification.Action action) {
        this.title = action.title.toString();

        if (action.getRemoteInputs() != null) {
            for(RemoteInput ri: action.getRemoteInputs()) {
                remoteInputs.add(new NotificationRemoteInputModel(ri));
            }
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NotificationActionModel{");
        sb.append("title='").append(title).append('\'');
        sb.append(", remoteInputs=").append(remoteInputs);
        sb.append('}');
        return sb.toString();
    }
}
