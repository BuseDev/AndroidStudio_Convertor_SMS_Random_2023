package com.example.mobilprogramlama;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;

public class SmsActivity extends AppCompatActivity {

    EditText editTextPhone;
    EditText editTextMessage;
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        editTextPhone = findViewById(R.id.editTextPhone);
        editTextMessage = findViewById(R.id.editTextMessage);
        sendButton = findViewById(R.id.send_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

            @Override
            public void onClick(View view) {
                // Önceden izin alınıp alınmadığını kontrol et
                if (ContextCompat.checkSelfPermission(SmsActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    // İzin alınmamışsa, kullanıcıdan izin iste
                    ActivityCompat.requestPermissions(SmsActivity.this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
                } else {
                    // İzin zaten varsa, SMS gönderme işlemini gerçekleştir
                    sendMessage();
                }
            }
        });
    }

    private void sendMessage(){
        String phoneNumber = editTextPhone.getText().toString();
        String message =editTextMessage.getText().toString();

        if(phoneNumber.isEmpty() || message.isEmpty()){
            Toast.makeText(this, "Telephone number and message area cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "Mesaj gönderildi", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(this, "Mesaj gönderilirken bir hata oluştu", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}