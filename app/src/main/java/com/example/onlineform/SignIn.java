package com.example.onlineform;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignIn extends AppCompatActivity {
  EditText mPassword1,mEmail1;
  Button mButton1,mButton2;
  FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in);
        mPassword1=findViewById(R.id.pass_log);
        mEmail1=findViewById(R.id.email_log);
        fAuth=FirebaseAuth.getInstance();
        mButton1=findViewById(R.id.button_1);
        mButton2=findViewById(R.id.button_2);
        if(fAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),Drawer.class));
            finish();
        }
        mButton1.setOnClickListener(v -> {
            String email=mEmail1.getText().toString().trim();
            String password=mPassword1.getText().toString().trim();
            if(TextUtils.isEmpty(email)){
                mEmail1.setError("Email is required");
                return;
            }
            if(TextUtils.isEmpty(password)){
                mPassword1.setError("Password is required");
                return;
            }
            if(password.length()<6){
                mPassword1.setError("Password must be >= to 6 characters!!");
            }
            fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task ->{
            if(task.isSuccessful()){
                Toast.makeText(SignIn.this,"User Logged in!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Drawer.class));
            }
            else
            {
                Toast.makeText(SignIn.this,"Error! "+ Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    });
       mButton2.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),SignUp.class)));
    }



}