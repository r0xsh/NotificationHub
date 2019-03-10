package me.antoinebagnaud.notificationhub.views;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import me.antoinebagnaud.notificationhub.R;
import me.antoinebagnaud.notificationhub.services.WebSocketsService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    @Override
    public void onStop() {
        super.onStop();
    }




    public void startService(View view) {
        Toast.makeText(this, "Starting....", Toast.LENGTH_LONG).show();

        String hostname = ((TextView) findViewById(R.id.ip)).getText().toString();

        Intent service = new Intent(this, WebSocketsService.class);
        service.putExtra("ADDRESS_IP", hostname);
        service.putExtra("ACTION", "START");
        ContextCompat.startForegroundService(this, service);
    }

    public void stopService(View view) {
        Toast.makeText(this, "Stopping....", Toast.LENGTH_LONG).show();
        stopService(new Intent(this, WebSocketsService.class));
    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void notificationAccess(View view) {
        startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
    }
}
