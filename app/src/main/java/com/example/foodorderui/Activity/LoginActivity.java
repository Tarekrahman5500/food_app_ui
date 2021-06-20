package com.example.foodorderui.Activity;

import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import com.example.foodorderui.MainActivity;
import com.example.foodorderui.R;
import com.example.foodorderui.databinding.ActivityLoginBinding;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    Boolean matchPass = false;
    Boolean mailPattern = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        //  binding.ForgotPasword.getPaint().setUnderlineText(true);


        // awesomeValidation.setContext(this);

        ChangeLoginSignUPColor(binding.CallLogin, binding.CallSignUp, binding.LoginLayout, binding.SignUpLayout);
        ChangeLoginSignUPColor(binding.CallSignUp, binding.CallLogin, binding.SignUpLayout, binding.LoginLayout);


        binding.signUpGoogle.setOnClickListener(v -> Toast.makeText(this, "google", Toast.LENGTH_SHORT).show());
        binding.signUpFB.setOnClickListener(v -> Toast.makeText(this, "FB", Toast.LENGTH_SHORT).show());

        ShowHidePass(binding.imageId, binding.userPassword);
        ShowHidePass(binding.imageId2, binding.userPasswordConfram);

        binding.loginButton.setOnClickListener(v -> {
            if (binding.loginMail.getText().toString().isEmpty()) {
                binding.loginMail.setError("Email can't be empty");
                return;
            }
            if (binding.loginPassword.getText().toString().isEmpty()) {
                binding.loginPassword.setError("Password can't be empty");
                return;
            }
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            binding.loginMail.setText(null);
            binding.loginPassword.setText(null);
        });


        //adding validation to edittexts

        binding.userPhoneNumber.setText(String.valueOf(+880));
        binding.signUpButton.setOnClickListener(v -> {

            if (validatePassword(binding.userPassword) | !validateConframPass(binding.userPassword, binding.userPasswordConfram)
                    | !validatePhoneNo(binding.userPhoneNumber) | !validateEmail(binding.userEmail) |
                    !validateUserName(binding.userName)
            ) {
                Toast.makeText(this, "Fill all the field correctly", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateConframPass(EditText p1, EditText p2) {
        if (p2.getText().toString().isEmpty()) {
            p2.setError("empty");
            return false;
        } else if (!p1.getText().toString().equals(p2.getText().toString())) {
         //   Toast.makeText(this, "here", Toast.LENGTH_SHORT).show();
            p1.setError("not match");
            p2.setError("not match");
            return false;

        } else {

            p1.setError(null);
            p2.setError(null);
            return true;
        }
    }


    private boolean validateUserName(EditText userName) {

        if (Objects.requireNonNull(userName).getText().toString().isEmpty()) {
            userName.setError("Field cannot be empty");
            return false;
        } else {
            userName.setError(null);
            return true;
        }
    }

    private boolean validateEmail(EditText p1) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (Objects.requireNonNull(p1).getText().toString().isEmpty()) {
            p1.setError("Field cannot be empty");
            return false;
        } else if (!Objects.requireNonNull(p1).getText().toString()
                .matches(emailPattern)) {
            p1.setError("Invalid email address");
            return false;

        } else {
            p1.setError(null);
            return true;
        }
    }

    private boolean validatePhoneNo(EditText p1) {
        if (Objects.requireNonNull(p1).getText().toString().isEmpty()) {
            p1.setError("Field cannot be empty");
            return false;
        } else if (Objects.requireNonNull(p1).getText().toString().length() != 13) {
            p1.setError("Invalid");
            return false;
        } else {
            p1.setError(null);
            return true;
        }
    }

    private boolean validatePassword(EditText p1) {
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


    private void ChangeLoginSignUPColor(TextView p1, TextView p2, ScrollView p3, ScrollView p4) {
        p1.setOnClickListener(v -> {
            p3.setVisibility(View.VISIBLE);
            p4.setVisibility(View.GONE);

            p2.setTextColor(ContextCompat.getColor(this, R.color.LogoText));
            p1.setTextColor(ContextCompat.getColor(this, R.color.white));
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
}