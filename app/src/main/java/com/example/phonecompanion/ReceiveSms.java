package com.example.phonecompanion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class ReceiveSms extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "Sms Received", Toast.LENGTH_SHORT).show();
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle=intent.getExtras();
            SmsMessage msgs;
            String msg_from;
            if(bundle!=null){
                try {
                   Object[] pdus=(Object[]) bundle.get("pdus");


                       msgs=SmsMessage.createFromPdu((byte[])pdus[0]);
                       msg_from=msgs.getOriginatingAddress();
                       String msgbody=msgs.getMessageBody();


                       Toast.makeText(context, "From : "+ msg_from + ", Body : " + msgbody, Toast.LENGTH_SHORT).show();

                       Intent smsIntent=new Intent(context,MainActivity.class);
                       smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       smsIntent.putExtra("MessageNumber",msg_from);
                       smsIntent.putExtra("Messages",msgbody);
                       context.startActivity(smsIntent);

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
