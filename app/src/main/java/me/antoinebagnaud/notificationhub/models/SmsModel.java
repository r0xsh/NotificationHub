package me.antoinebagnaud.notificationhub.models;

public class SmsModel {

    private String sender;

    private String body;

    public SmsModel(String sender, String body) {
        this.sender = sender;
        this.body = body;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SmsModel{");
        sb.append("sender='").append(sender).append('\'');
        sb.append(", body='").append(body).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
