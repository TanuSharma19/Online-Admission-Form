package com.example.onlineform;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Thread.sleep;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread thread= new Thread(() -> {
            try {
                sleep(2000);
                Intent i=new Intent(SplashScreen.this,SignIn.class);
                startActivity(i);
                finish();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        });
        thread.start();
    }
}