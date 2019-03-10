package me.antoinebagnaud.notificationhub.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.gson.Gson;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import me.antoinebagnaud.notificationhub.R;
import me.antoinebagnaud.notificationhub.models.NotificationModel;
import me.antoinebagnaud.notificationhub.models.SmsModel;
import me.antoinebagnaud.notificationhub.views.MainActivity;
import timber.log.Timber;

import static me.antoinebagnaud.notificationhub.App.CHANNEL_ID;

public class WebSocketsService extends Service {

    private WebSocket ws = null;

    private Gson gson = new Gson();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent.getStringExtra("ACTION") != null) {
            switch (intent.getStringExtra("ACTION")) {
                case "STOP":
                    stopSelf();
                    break;
                case "START":
                    initService(intent);
                    break;
            }
        }

        return START_REDELIVER_INTENT;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotificationModel(NotificationModel event) {

        if (ws.isOpen()) {
            ws.sendText(gson.toJson(event));
        } else {
            Timber.d("Websocket not opened");
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSmsModel(SmsModel event) {

        if (ws.isOpen()) {
            ws.sendText(gson.toJson(event));
        } else {
            Timber.d("Websocket not opened");
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        ws.disconnect();
    }


    /**
     * Init the service
     * @param intent {@link Intent}
     */
    private void initService(Intent intent) {
        EventBus.getDefault().register(this);
        WebSocketFactory factory = new WebSocketFactory().setConnectionTimeout(5000);

        Notification notification = buildNotification();
        startForeground(1, notification);

        try {
            ws = factory.createSocket("ws://" + intent.getStringExtra("ADDRESS_IP") +":8888/ws/");
            ws.addListener(new WebSocketListener(this));

            ws.connectAsynchronously();
        } catch (IOException e) {
            stopSelf();
            Timber.e(e);
        }

    }

    /**
     * Init the ServiceForeground notification
     * @return Notification
     */
    private Notification buildNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Intent stopIntent = new Intent(this, WebSocketsService.class);
        stopIntent.putExtra("ACTION", "STOP");
        PendingIntent pendingStop = PendingIntent.getService(this, 0, stopIntent, 0);

        NotificationCompat.Action stopAction = new NotificationCompat.Action(R.drawable.ic_launcher_background, "Stop", pendingStop);

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("NotificationHub")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .addAction(stopAction)
                .setContentIntent(pendingIntent)
                .build();
    }
}
