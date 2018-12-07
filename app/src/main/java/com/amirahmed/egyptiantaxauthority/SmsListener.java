package com.amirahmed.egyptiantaxauthority;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction()))
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                String messageBody = smsMessage.getMessageBody();
                String newmessageBody = messageBody.substring(messageBody.lastIndexOf(":") + 1);
                Toast.makeText(context, newmessageBody, Toast.LENGTH_SHORT).show();
            }
    }

}