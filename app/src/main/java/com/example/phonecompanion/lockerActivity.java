package com.example.phonecompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.provider.Contacts.SettingsColumns.KEY;

public class lockerActivity extends AppCompatActivity {

    EditText key,value;
    Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locker);

         key= (EditText) findViewById(R.id.key);
         value=(EditText) findViewById(R.id.value);
         save=(Button) findViewById(R.id.save);



         save.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 String k=key.getText().toString();
                 String val=value.getText().toString();
                 if(TextUtils.isEmpty(k) || TextUtils.isEmpty(val)){
                     Toast.makeText(lockerActivity.this, "Please Enter the Credential", Toast.LENGTH_SHORT).show();
                 }
                 else{
                     globalClass.vvaalluuee=val;
                     globalClass.kkeeyy=k;
                     Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                     startActivity(intent);
                 }
             }
         });



    }
}
