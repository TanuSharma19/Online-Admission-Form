package com.example.onlineform;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.common.collect.Range;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class GraduateForm extends AppCompatActivity {
    EditText stName,fName,mName,mdob,mPhn,add,skName,skName1,colName,mMark1,mMark2,mMark3;
    TextView mCourse,tStream,tGrad;
    RadioGroup mSex;
    RadioButton mMale,mFemale,mOther;
    Spinner core4,mStream,mGrad;
    Button mSubmit;
    AwesomeValidation awesomeValidation;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graduate_form);
        stName=findViewById(R.id.stud_name);
        fName=findViewById(R.id.father);
        mName=findViewById(R.id.mother);
        mdob=findViewById(R.id.dob);
        mPhn=findViewById(R.id.phn);
        add=findViewById(R.id.address);
        skName=findViewById(R.id.skul_1);
        skName1=findViewById(R.id.skul_2);
        colName=findViewById(R.id.collge);
        mMark1=findViewById(R.id.marks1);
        mMark2=findViewById(R.id.marks2);
        mMark3=findViewById(R.id.marks_3);
        mSex=findViewById(R.id.sex);
        mMale=findViewById(R.id.male);
        mFemale=findViewById(R.id.female);
        mOther=findViewById(R.id.other);
        core4=findViewById(R.id.course);
        mSubmit=findViewById(R.id.submit);
        mCourse=findViewById(R.id.courses);
        mStream=findViewById(R.id.stream);
        tStream=findViewById(R.id.streams);
        mGrad=findViewById(R.id.grad);
        tGrad=findViewById(R.id.grad_t);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        //for validation of name

        awesomeValidation.addValidation(this,R.id.stud_name, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.father, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.mother, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.phn, "[5-9]{1}[0-9]{9}$", R.string.invalid_phone);
        awesomeValidation.addValidation(this,R.id.address, RegexTemplate.NOT_EMPTY,R.string.invalid_address);
        awesomeValidation.addValidation(this,R.id.skul_1, RegexTemplate.NOT_EMPTY,R.string.invalid_school);
        awesomeValidation.addValidation(this,R.id.skul_2, RegexTemplate.NOT_EMPTY,R.string.invalid_school);
        awesomeValidation.addValidation(this, R.id.marks1, Range.closed(0.00f,100.00f), R.string.invalid_percent);
        awesomeValidation.addValidation(this, R.id.marks2, Range.closed(0.00f,100.00f), R.string.invalid_percent);
        awesomeValidation.addValidation(this, R.id.marks_3, Range.closed(0.00f,100.00f), R.string.invalid_percent);
        awesomeValidation.addValidation(this, R.id.dob, input -> {
            // check if the age is >= 18
            try {
                Calendar calendarBirthday = Calendar.getInstance();
                Calendar calendarToday = Calendar.getInstance();
                calendarBirthday.setTime(Objects.requireNonNull(new SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(input)));
                int yearOfToday = calendarToday.get(Calendar.YEAR);
                int yearOfBirthday = calendarBirthday.get(Calendar.YEAR);
                if (yearOfToday - yearOfBirthday > 21) {
                    return true;
                } else if (yearOfToday - yearOfBirthday == 21) {
                    int monthOfToday = calendarToday.get(Calendar.MONTH);
                    int monthOfBirthday = calendarBirthday.get(Calendar.MONTH);
                    if (monthOfToday > monthOfBirthday) {
                        return true;
                    } else if (monthOfToday == monthOfBirthday) {
                        if (calendarToday.get(Calendar.DAY_OF_MONTH) >= calendarBirthday.get(Calendar.DAY_OF_MONTH)) {
                            return true;
                        }
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return false;
        }, R.string.err_birth);
        mSubmit.setOnClickListener(v -> {
            String name1=stName.getText().toString().trim();
            String father=fName.getText().toString().trim();
            String mother=mName.getText().toString().trim();
            final String gender = ((RadioButton)findViewById(mSex.getCheckedRadioButtonId())).getText().toString();
            String birthday=mdob.getText().toString();
            String phn=mPhn.getText().toString();
            String address=add.getText().toString();
            String school_10=skName.getText().toString();
            String marks_10=mMark1.getText().toString();
            String school_12=skName1.getText().toString();
            String stream=mStream.getSelectedItem().toString();
            String marks_12=mMark2.getText().toString();
            String college=colName.getText().toString();
            String course=mGrad.getSelectedItem().toString();
            String marks_grad=mMark3.getText().toString();
            String selected_course=core4.getSelectedItem().toString();

            if (mGrad.getSelectedItem().toString().trim().equals("Select the course")) {
                tGrad.setError("select valid Course");
                return;
            }
            if (mStream.getSelectedItem().toString().trim().equals("Select your stream")) {
                tStream.setError("select valid Stream");
                return;
            }
            if (core4.getSelectedItem().toString().trim().equals("Select the course")) {
                mCourse.setError("select valid course");
                return;
            }
            if (mSex.getCheckedRadioButtonId() <= 0) {//Grp is your radio group object
                mOther.setError("Select Item");
                return;//Set error to last Radio button
            }

            if (awesomeValidation.validate()) {

                Toast.makeText(getApplicationContext(), "Validation Success", Toast.LENGTH_SHORT).show();

            }
            else
            {
                Toast.makeText(getApplicationContext(),"Validation failed",Toast.LENGTH_SHORT).show();
            }
            userId= Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
            DocumentReference documentReference = fStore.collection("Form2 detail").document(userId);
            Map<String,Object> userForm=new HashMap<>();
            userForm.put("fullname",name1);
            userForm.put("Father's name",father);
            userForm.put("Mother's name",mother);
            userForm.put("Gender",gender);
            userForm.put("Date of birth",birthday);
            userForm.put("Phone number",phn);
            userForm.put("Address",address);
            userForm.put("School name(10th)",school_10);
            userForm.put("10th Marks",marks_10);
            userForm.put("School name(12th)",school_12);
            userForm.put("Stream",stream);
            userForm.put("12th Marks",marks_12);
            userForm.put("College name",college);
            userForm.put("Graduation Course",course);
            userForm.put("Graduation Marks",marks_grad);
            userForm.put("Course you want to enroll in",selected_course);
            documentReference.set(userForm).addOnSuccessListener(aVoid -> {
                Log.v("TAG","On Success: User profile is created for"+userId);
                startActivity(new Intent(getApplicationContext(),SubmitForm2.class));
            });
        });



    }


}