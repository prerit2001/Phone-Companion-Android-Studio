package com.example.phonecompanion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    String address,message;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    String messageSent="Message Sent";
    Button contact,locker,help;

    HashMap<String, String> map = new HashMap<>();
    ArrayList<String> arrayList1,arrayList2,arrayList3,arrayList4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if (ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)) {
            }
            else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED ){
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS},1000);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED ){
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},1);
        }

        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        SharedPreferences sharedPreferences=getSharedPreferences("com.example.phonecompanion",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();



        arrayList1=new ArrayList<>();
        arrayList2=new ArrayList<>();
        arrayList3=new ArrayList<>();
        arrayList4=new ArrayList<>();

        arrayList3.add("testing");
        arrayList4.add("test");


        contact=(Button) findViewById(R.id.contacts);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),contactsActivity.class);
                startActivity(intent);
            }
        });



        locker=(Button) findViewById(R.id.locker);
        help=(Button) findViewById(R.id.help);

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),helpActivity.class);
                startActivity(intent);
            }
        });

        locker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),lockerActivity.class);
                startActivity(intent);
            }
        });

        Cursor cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        while(cursor.moveToNext()){
            String  name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String mobile=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            arrayList1.add(name);
            arrayList2.add(mobile);
        }

        if(globalClass.kkeeyy!="NULL" && globalClass.vvaalluuee!="NULL"){
            arrayList3.add(globalClass.kkeeyy);
            arrayList4.add(globalClass.vvaalluuee);
            arrayList1.add(globalClass.kkeeyy);
            arrayList2.add(globalClass.vvaalluuee);

            editor.putString(globalClass.kkeeyy,globalClass.vvaalluuee);

            editor.apply();
        }

        Bundle extras =getIntent().getExtras();
        if(extras!=null){
           address=extras.getString("MessageNumber");
           message=extras.getString("Messages");

            TextView addressfield=(TextView) findViewById(R.id.address);
            TextView messagefield=(TextView) findViewById(R.id.messages);
            addressfield.setText("Message From : "+address);
            messagefield.setText("Message : "+message);


            if(message!=null && message.charAt(0)=='#' && message.charAt(message.length()-1)=='#' ){
                if(address!=null && (address.charAt(0)=='+' || Character.isDigit(address.charAt(0)) ) && address.length()>=10 && address.length()<=13 ){
                    TextView addressto=(TextView) findViewById(R.id.addressTo);
                    TextView messageTo=(TextView) findViewById(R.id.messagesTo);


                    String mesageRetrival= message.substring(1,message.length()-1);
                    String profile="NORMAL_MODE";



                    Boolean flag=arrayList1.contains(mesageRetrival);
                    Boolean glag=arrayList3.contains(mesageRetrival);
           //         SharedPreferences sharedPreferences=getSharedPreferences("com.example.phonecompanion",Context.MODE_PRIVATE);
                    String zz=sharedPreferences.getString(mesageRetrival,"null");

                    if(mesageRetrival.compareTo(profile)==0){
                                Log.i("MyApp","Silent mode");
                                am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                messageSent="Profile Set Normal";
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(address, null, messageSent, null,  null);
                                Toast.makeText(getApplicationContext(),"Message Sent" , Toast.LENGTH_LONG).show();
                                addressto.setText("Message From : "+address);
                                messageTo.setText("Message : "+messageSent);
                    }
                    else if(zz.compareTo("null")!=0){
                        messageSent=zz;
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(address, null, messageSent, null,  null);
                        Toast.makeText(getApplicationContext(),"Message Sent" , Toast.LENGTH_LONG).show();
                        addressto.setText("Message From : "+address);
                        messageTo.setText("Message : "+messageSent);
                    }

//                    else if(glag){
//                      messageSent=arrayList4.get(arrayList3.indexOf(mesageRetrival));
//                      SmsManager smsManager = SmsManager.getDefault();
//                      smsManager.sendTextMessage(address, null, messageSent, null,  null);
//                      Toast.makeText(getApplicationContext(),"Message Sent" , Toast.LENGTH_LONG).show();
//                      addressto.setText("Message From : "+address);
//                      messageTo.setText("Message : "+messageSent);
//                    }

                     else if(flag){
                        messageSent=arrayList2.get(arrayList1.indexOf(mesageRetrival));
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(address, null, messageSent, null,  null);
                        Toast.makeText(getApplicationContext(),"Message Sent" , Toast.LENGTH_LONG).show();
                      addressto.setText("Message From : "+address);
                      messageTo.setText("Message : "+messageSent);
                    }
                    else {
                        Toast.makeText(this, "Not Found Contact"+ mesageRetrival +"Not Found", Toast.LENGTH_SHORT).show();
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(address, null,"Not Found Contact ", null,  null);
                      addressto.setText("Message From : "+address);
                      messageTo.setText("Message : "+"NOT FOUND");
                    }


                }
            }


        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1000){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted !!!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "XXX-- Permission Denied --XXX", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        else if(requestCode==0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(getApplicationContext(),"SMS Not Sent, please try again.", Toast.LENGTH_LONG).show();
                return;
            }
        }
        else  if(requestCode==1){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
