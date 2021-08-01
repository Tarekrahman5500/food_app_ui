package com.example.foodorderui.Helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.*;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import com.example.foodorderui.Activity.SettingActivity;
import com.example.foodorderui.R;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class Methods {

    public static boolean validateConframPass(EditText p1, EditText p2) {
        if (p2.getText().toString().isEmpty()) {
            p2.setError(" Field cannot be empty");
            return false;
        } else if (!p1.getText().toString().equals(p2.getText().toString())) {
            //   Toast.makeText(this, "here", Toast.LENGTH_SHORT).show();
            p1.setError("password not match");
            p2.setError("password not match");
            return false;

        } else {

            p1.setError(null);
            p2.setError(null);
            return true;
        }
    }


    public static boolean validateUserName(EditText userName) {

        if (Objects.requireNonNull(userName).getText().toString().isEmpty()) {
            userName.setError("Field cannot be empty");
            return true;
        } else {
            userName.setError(null);
            return false;
        }
    }

    public static boolean validateEmail(EditText p1) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (Objects.requireNonNull(p1).getText().toString().isEmpty()) {
            p1.setError("Field cannot be empty");
            return true;
        } else if (!Objects.requireNonNull(p1).getText().toString()
                .matches(emailPattern)) {
            p1.setError("Invalid email address");
            return true;

        } else {
            p1.setError(null);
            return false;
        }
    }

    public static boolean validatePhoneNo(EditText p1) {
        if (Objects.requireNonNull(p1).getText().toString().isEmpty()) {
            p1.setError("Field cannot be empty");
            return true;
        } else if (Objects.requireNonNull(p1).getText().toString().length() != 13) {
            p1.setError("Invalid");
            return true;
        } else {
            p1.setError(null);
            return false;
        }
    }

    public static boolean validatePassword(EditText p1) {
        String passwordVal = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (Objects.requireNonNull(p1).getText().toString().isEmpty()) {
            p1.setError("Field cannot be empty");
            return true;
        } else if (!Objects.requireNonNull(p1).getText().toString().matches(passwordVal)) {
            p1.setError("Password is too weak");
            return true;
        } else {
            p1.setError(null);
            return false;
        }
    }


    public static void ChangeLoginSignUPColor(TextView p1, TextView p2, ScrollView p3, ScrollView p4, Activity activity) {
        p1.setOnClickListener(v -> {
            p3.setVisibility(View.VISIBLE);
            p4.setVisibility(View.GONE);

            p2.setTextColor(ContextCompat.getColor(activity, R.color.LogoText));
            p1.setTextColor(ContextCompat.getColor(activity, R.color.white));
        });
    }

    public static void ShowHidePass(ImageView p, EditText p2) {
        p.setOnClickListener(v -> {

            if (v.getId() == p.getId()) {
                if (p2.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    p.setImageResource(R.drawable.ic_view);

                    //Show Password
                    p2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    p.setImageResource(R.drawable.ic_pass_show);

                    //Hide Password
                    p2.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });
    }

    public static void ChangeColor(TextView p1, LinearLayout p2, Activity activity) {
        p1.setOnClickListener(v -> {
            if (p2.getVisibility() == View.VISIBLE) {
                p2.setVisibility(View.GONE);
                p1.setTextColor(ContextCompat.getColor(activity, R.color.LogoText));
            } else {
                p2.setVisibility(View.VISIBLE);
                p1.setTextColor(ContextCompat.getColor(activity, R.color.white));
            }
        });
    }

    @SuppressLint({"ResourceType"})
    public static void changeOtherColor(ImageView p1, ImageView p2, ImageView p3, ImageView p4, ImageView p5,ImageView p6,
                                        ConstraintLayout l1, ConstraintLayout l2, ConstraintLayout l3,
                                        ConstraintLayout l4, ConstraintLayout l5, ConstraintLayout l6, Activity activity) {
        SettingActivity settingActivity = new SettingActivity();
        AtomicReference<String> send = new AtomicReference<>("pizza");


           // p1.setBackgroundResource( R.drawable.category_background5);
            p1.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
            p2.setBackgroundColor(ContextCompat.getColor(activity, R.color.FoodColor));
            p3.setBackgroundColor(ContextCompat.getColor(activity, R.color.FoodColor));
            p4.setBackgroundColor(ContextCompat.getColor(activity, R.color.FoodColor));
            p5.setBackgroundColor(ContextCompat.getColor(activity, R.color.FoodColor));
            p6.setBackgroundColor(ContextCompat.getColor(activity, R.color.FoodColor));
            l1.setVisibility(View.VISIBLE);
            l2.setVisibility(View.GONE);
            l3.setVisibility(View.GONE);
            l4.setVisibility(View.GONE);
            l5.setVisibility(View.GONE);
            l6.setVisibility(View.GONE);


        //    settingActivity.ge




    }
}
