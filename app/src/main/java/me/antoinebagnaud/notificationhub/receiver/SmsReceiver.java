package me.antoinebagnaud.notificationhub.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;

import org.greenrobot.eventbus.EventBus;

import me.antoinebagnaud.notificationhub.models.SmsModel;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                String messageBody = smsMessage.getMessageBody();
                String sender = smsMessage.getDisplayOriginatingAddress();
                EventBus.getDefault().post(new SmsModel(sender, messageBody));
            }
        }

    }
}
