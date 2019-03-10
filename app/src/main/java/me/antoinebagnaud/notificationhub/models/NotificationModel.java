package me.antoinebagnaud.notificationhub.models;

import java.util.Arrays;

public class NotificationModel {

    private String packageName;

    private String title;

    private String content;

    private int color;

    private transient android.app.Notification.Action[] actions;

    public NotificationModel(String packageName, String title, String content, int color, android.app.Notification.Action[] actions) {
        this.packageName = packageName;
        this.title = title;
        this.content = content;
        this.color = color;
        this.actions = actions;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NotificationModel{");
        sb.append("packageName='").append(packageName).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", color=").append(Integer.toHexString(color));
        sb.append(", actions=").append(actions == null ? "null" : Arrays.asList(actions).toString());
        sb.append('}');
        return sb.toString();
    }
}
