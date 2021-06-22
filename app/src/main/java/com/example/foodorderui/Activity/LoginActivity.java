package com.example.foodorderui.Activity;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.foodorderui.Helper.DatabaseMethods;
import com.example.foodorderui.Helper.Methods;
import com.example.foodorderui.MainActivity;
import com.example.foodorderui.Model.UserModel;
import com.example.foodorderui.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    boolean validate_data = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        //  binding.ForgotPasword.getPaint().setUnderlineText(true);
        // change the color or login and signup icon when switch position
        Methods.ChangeLoginSignUPColor(binding.CallLogin, binding.CallSignUp, binding.LoginLayout, binding.SignUpLayout, this);
        Methods.ChangeLoginSignUPColor(binding.CallSignUp, binding.CallLogin, binding.SignUpLayout, binding.LoginLayout, this);


        binding.signUpGoogle.setOnClickListener(v -> Toast.makeText(this, "google", Toast.LENGTH_SHORT).show());
        binding.signUpFB.setOnClickListener(v -> Toast.makeText(this, "FB", Toast.LENGTH_SHORT).show());

        // for create account password and confram password
        Methods.ShowHidePass(binding.imageId, binding.userPassword);
        Methods.ShowHidePass(binding.imageId2, binding.userPasswordConfram);

        // for login time
        Methods.ShowHidePass(binding.imageId3, binding.loginPassword);

        binding.loginButton.setOnClickListener(v -> {
            if (binding.loginMail.getText().toString().isEmpty()) {
                binding.loginMail.setError("Email can't be empty");
                return;
            }
            if (binding.loginPassword.getText().toString().isEmpty()) {
                binding.loginPassword.setError("Password can't be empty");
                return;
            }
            // check that the user data is in database Authentication table or not
            auth.signInWithEmailAndPassword
                    (binding.loginMail.getText().toString(), binding.loginPassword.getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this, Objects.requireNonNull(task.getException()).getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            //  startActivity(new Intent(LoginActivity.this, MainActivity.class));

            //binding.loginMail.setText(null);
            // binding.loginPassword.setText(null);
        });


        binding.userPhoneNumber.setText(String.valueOf(880));
        // validity check before signup
        binding.signUpButton.setOnClickListener(this::onClick);


    }

     DatabaseMethods databaseMethods = new DatabaseMethods();
    private void onClick(View v) {

        if (Methods.validatePassword(binding.userPassword) | !Methods.validateConframPass(binding.userPassword, binding.userPasswordConfram)
                | Methods.validatePhoneNo(binding.userPhoneNumber) | Methods.validateEmail(binding.userEmail) |
                Methods.validateUserName(binding.userName)
               // databaseMethods.CheckPhoneEmailDuplicate(binding.userPhoneNumber, binding.userEmail)
        ) {

            Toast.makeText(this, "Fill all the field correctly", Toast.LENGTH_SHORT).show();
        }  else {

            // add data to firebase here unique key is phone number
            try {
                auth.createUserWithEmailAndPassword(
                        binding.userEmail.getText().toString(),
                        binding.userPassword.getText().toString()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        UserModel userModel = new UserModel(
                                binding.userName.getText().toString(),
                                binding.userPhoneNumber.getText().toString(),
                                binding.userEmail.getText().toString(),
                                binding.userPassword.getText().toString());
                        String id = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getUser()).getUid();
                        database.getReference().child("users").child(id).setValue(userModel);
                        Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, Objects.requireNonNull(task.getException()).getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });


            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

}


