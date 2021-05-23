package com.example.onlineform;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Drawer extends AppCompatActivity {
    TextView name;
    NavigationView nav;
 ActionBarDrawerToggle toggle;
 DrawerLayout drawerLayout;
 Toolbar toolbar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;



    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nav=findViewById(R.id.navmenu);
        drawerLayout=findViewById(R.id.drawer);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId())
            {
                case R.id.menu_home:
                    Toast.makeText(getApplicationContext(),"Home panel is open",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    break;
                case R.id.menu_form1:
                    Toast.makeText(getApplicationContext(),"form panel is open",Toast.LENGTH_SHORT).show();
                    DocumentReference docIdRef = fStore.collection("Form detail").document(userId);
                    docIdRef.get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            assert document != null;
                            if (document.exists()) {
                                Log.d("TAG", "Document exists!");
                                startActivity(new Intent(getApplicationContext(),SubmitForm.class));
                            } else {
                                Log.d("TAG", "Document does not exist!");
                                startActivity(new Intent(getApplicationContext(),AdmForm.class));
                            }
                        } else {
                            Log.d("TAG", "Failed with: ", task.getException());
                        }
                    });
                    break;
                case R.id.menu_form2:
                    Toast.makeText(getApplicationContext(),"form panel is open",Toast.LENGTH_SHORT).show();
                    DocumentReference Ref = fStore.collection("Form2 detail").document(userId);
                    Ref.get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            assert document != null;
                            if (document.exists()) {
                                Log.d("TAG", "Document exists!");
                                startActivity(new Intent(getApplicationContext(),SubmitForm2.class));
                            } else {
                                Log.d("TAG", "Document does not exist!");
                                startActivity(new Intent(getApplicationContext(),GraduateForm.class));
                            }
                        } else {
                            Log.d("TAG", "Failed with: ", task.getException());
                        }
                    });
                    break;
                case R.id.menu_logout:
                    Toast.makeText(getApplicationContext(),"User Logged out",Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(this, SignIn.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.startActivity(intent);
                    finish();
                    break;
            }
            return true;
        });
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        userId= Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, (value, error) -> {
            assert value != null;
            View navheader=nav.getHeaderView(0);
            name =navheader.findViewById(R.id.get_name);
            name.setText(value.getString("fullname"));
        });

    }
}