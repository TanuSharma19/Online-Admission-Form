package com.example.onlineform;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SubmitForm2 extends AppCompatActivity {
    TextView name_1,name_2,name_3,name_4,name_5,name_6,name_7,name_8,name_9,name_10,name_11,name_12,name_13,name_14,name_15,name_16;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_form2);
        name_1=findViewById(R.id.detail_1);
        name_2=findViewById(R.id.detail_2);
        name_3=findViewById(R.id.detail_3);
        name_4=findViewById(R.id.detail_4);
        name_5=findViewById(R.id.detail_5);
        name_6=findViewById(R.id.detail_6);
        name_7=findViewById(R.id.detail_7);
        name_8=findViewById(R.id.detail_8);
        name_9=findViewById(R.id.detail_9);
        name_10=findViewById(R.id.detail_10);
        name_11=findViewById(R.id.detail_11);
        name_12=findViewById(R.id.detail_12);
        name_13=findViewById(R.id.detail_13);
        name_14=findViewById(R.id.detail_14);
        name_15=findViewById(R.id.detail_15);
        name_16=findViewById(R.id.detail_16);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        userId= Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        DocumentReference documentReference = fStore.collection("Form2 detail").document(userId);
        documentReference.addSnapshotListener(this, (value, error) -> {
            assert value != null;
            name_1.setText(value.getString("fullname"));
            name_2.setText(value.getString("Father's name"));
            name_3.setText(value.getString("Mother's name"));
            name_4.setText(value.getString("Gender"));
            name_5.setText(value.getString("Date of birth"));
            name_6.setText(value.getString("Phone number"));
            name_7.setText(value.getString("Address"));
            name_8.setText(value.getString("School name(10th)"));
            name_9.setText(value.getString("10th Marks"));
            name_10.setText(value.getString("School name(12th)"));
            name_11.setText(value.getString("Stream"));
            name_12.setText(value.getString("12th Marks"));
            name_13.setText(value.getString("College name"));
            name_14.setText(value.getString("Graduation Course"));
            name_15.setText(value.getString("Graduation Marks"));
            name_16.setText(value.getString("Course you want to enroll in"));

        });
    }

    public void back_button(View view) {
        startActivity(new Intent(getApplicationContext(),Drawer.class));
    }
}