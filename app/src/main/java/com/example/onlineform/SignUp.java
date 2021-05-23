package com.example.onlineform;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUp extends AppCompatActivity {
    EditText mFullName,mPhone,mEmail,mPassword,mConfirmPass;
    Button mRegister;
    TextView mLogin;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mFullName= findViewById(R.id.name);
        mPhone= findViewById(R.id.user_name);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mConfirmPass = findViewById(R.id.confirm_pass);
        mRegister = findViewById(R.id.button_register);
        mLogin=findViewById(R.id.login_text);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        if(fAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),Drawer.class));
            finish();
        }
        mRegister.setOnClickListener(v -> {
            String email=mEmail.getText().toString().trim();
            String password=mPassword.getText().toString().trim();
            String confirmPass=mConfirmPass.getText().toString().trim();
            String name=mFullName.getText().toString();
            String phone=mPhone.getText().toString();
            if(TextUtils.isEmpty(email)){
                mEmail.setError("Email is required");
                return;
            }
            if(TextUtils.isEmpty(password)){
                mPassword.setError("Password is required");
                return;
            }
            if(TextUtils.isEmpty(confirmPass))
            {
                mConfirmPass.setError("confirm password is required");
                return;
            }
            if(password.length()<6){
                mPassword.setError("Password must be >= to 6 characters!!");
            }
            if(!password.equals(confirmPass))
            {
                mConfirmPass.setError("Passwors is not matching.");
                return;
            }
            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(SignUp.this,"User Created",Toast.LENGTH_SHORT).show();
                    userId=fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fstore.collection("users").document(userId);
                    Map<String,Object> user=new HashMap<>();
                    user.put("fullname",name);
                    user.put("email",email);
                    user.put("phone",phone);
                    documentReference.set(user).addOnSuccessListener(aVoid -> Log.v("TAG","On Success: User profile is created for"+userId));
                    startActivity(new Intent(getApplicationContext(),Drawer.class));
                }
                else
                {
                  Toast.makeText(SignUp.this,"Error! "+ Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        });
        mLogin.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),SignIn.class)));
        }
}