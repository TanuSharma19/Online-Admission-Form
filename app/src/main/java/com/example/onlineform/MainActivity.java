package com.example.onlineform;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
     TextView name,email,phnum;
     FirebaseAuth fAuth;
     FirebaseFirestore fStore;
     String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=findViewById(R.id.textView2);
        email=findViewById(R.id.textView3);
        phnum=findViewById(R.id.textView4);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        userId= Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, (value, error) -> {
            assert value != null;
            name.setText(value.getString("fullname"));
            email.setText(value.getString("email"));
            phnum.setText(value.getString("phone"));

        });
    }




}