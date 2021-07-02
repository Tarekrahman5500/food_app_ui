package com.example.foodorderui.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.foodorderui.Helper.DatabaseMethods;
import com.example.foodorderui.Helper.Methods;
import com.example.foodorderui.MainActivity;
import com.example.foodorderui.Model.UserModel;
import com.example.foodorderui.R;
import com.example.foodorderui.databinding.ActivityUserBinding;
import com.example.foodorderui.databinding.BottomCardBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class UserActivity extends AppCompatActivity {
    ActivityUserBinding binding;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseStorage storage;

    String user_name;
    String user_email;
    String user_phoneNo;
    String user_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        storage = FirebaseStorage.getInstance();
        // binding.updateUserName.setText(FirebaseAuth.getInstance().getUid());

        // change of my user profile update field any show layout
        Methods.ChangeColor(binding.updatedeatilsText, binding.updateLayout, this);
        // change of my order field any show layout
        Methods.ChangeColor(binding.textView25, binding.ordersLayout, this);
        // change  of payment field any show layout
        Methods.ChangeColor(binding.textView29, binding.paymentLayout, this);
        // change  of Location field any show layout
        Methods.ChangeColor(binding.textView30, binding.locationLayout, this);


        binding.updatePhoneNumber.setText(String.valueOf(880));

        binding.bottomCard.BottomHomeButton.setOnClickListener(v ->
                startActivity(new Intent(UserActivity.this, MainActivity.class)));

        // binding.updatePassword.setText("123456");
        Methods.ShowHidePass(binding.imageId, binding.updatePassword);

        //  binding.updateButton.setEnabled(false);

        //show data to all fields
        ShowCurrentUserData();

        // add profile pic
        binding.AddPic.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            // for any type of file */*
            intent.setType("image/*");
            someActivityResultLauncher.launch(intent);
        });


        // update the user data

        binding.updateButton.setOnClickListener(this::onClick);

    }


    // change the profile pic
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();
                    assert data != null;
                    if (data.getData() != null) {
                        Uri file = data.getData();
                        binding.ProfileImage.setImageURI(file);
                        //   binding.updateButton.setEnabled(true);

                        final StorageReference reference = storage.getReference().child("profilePicture")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));

                        reference.putFile(file).addOnSuccessListener(taskSnapshot ->
                                reference.getDownloadUrl().addOnSuccessListener(uri -> {
                                    databaseReference.child(FirebaseAuth.getInstance().getUid())
                                            .child("profilePic").setValue(uri.toString());

                                    Toast.makeText(this, "Profile Picture Update", Toast.LENGTH_SHORT).show();
                                }));
                    }
                }
            });


    private void ShowCurrentUserData() {

        databaseReference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel users = snapshot.getValue(UserModel.class);
                assert users != null;
                Picasso.get().load(users.getProfilePic()).placeholder(R.drawable.ic_man).into(binding.ProfileImage);
                user_name = users.getUserName();
                user_phoneNo = users.getPhone();
                user_email = users.getEmail();
                user_password = users.getPassword();

                // set all data
                binding.updateEmail.setText(user_email);
                binding.updatePassword.setText(user_password);
                binding.updatePhoneNumber.setText(user_phoneNo);
                binding.updateUserName.setText(user_name);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // object of login class
    DatabaseMethods databaseMethods = new DatabaseMethods();


    private void onClick(View v) {
        if (Methods.validatePassword(binding.updatePassword) |
                Methods.validatePhoneNo(binding.updatePhoneNumber) |
                Methods.validateEmail(binding.updateEmail) |
                Methods.validateUserName(binding.updateUserName)

        ) {
            Toast.makeText(this, "Fill all the field correctly", Toast.LENGTH_SHORT).show();
        } else {
            updateUserData();
            //Toast.makeText(this, "User Update", Toast.LENGTH_SHORT).show();

          //  Query query = databaseReference.orderByChild("admin").equalTo("true");

        }

    }

    private void updateUserData() {
        if (isNameChanged() || isPasswordChanged() || isEmailChanged() || isPhoneChanged()) {
            Toast.makeText(this, "Data has been Update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data is Same", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isPhoneChanged() {
        // binding.updateButton.setEnabled(false);

        if (!user_phoneNo.equals(Objects.requireNonNull(binding.updatePhoneNumber).getText().toString())) {
            databaseReference.orderByChild("phone").equalTo(binding.updatePhoneNumber.getText().toString())
                    .addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            while (snapshot.exists()) {
                                if (snapshot.getValue() != null) {
                                    //it means user already registered
                                    binding.updatePhoneNumber.setError("Phone Number Already Use");
                                    Toast.makeText(UserActivity.this, "Phone Number Already Use", Toast.LENGTH_SHORT).show();
                                    // isPhoneChanged();
                                    //   binding.updateButton.setEnabled(false);
                                    break;
                                } else {
                                    //It is new users
                                    //write an entry to your user table
                                    //writeUserEntryToDB();
                                    //  binding.updateButton.setEnabled(true);
                                    databaseReference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                            .child("phone").setValue(binding.updatePhoneNumber.getText().toString());
                                    user_phoneNo = binding.updatePhoneNumber.getText().toString();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
            return true;
        } else return false;
    }

    private boolean isEmailChanged() {
        //   binding.updateButton.setEnabled(false);
        if (!user_email.equals(Objects.requireNonNull(binding.updateEmail).getText().toString())) {
            FirebaseAuth mAuth;
            mAuth = FirebaseAuth.getInstance();
            mAuth.fetchSignInMethodsForEmail(binding.updateEmail.getText().toString()).addOnCompleteListener(task -> {

                //  Log.d(TAG,""+task.getResult().getSignInMethods().size());
                if (Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getSignInMethods()).size() == 0) {
                    // email not existed
                    //  binding.updateButton.setEnabled(true);
                    databaseReference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                            .child("email").setValue(binding.updateEmail.getText().toString());
                    user_password = binding.updateEmail.getText().toString();

                } else {
                    // email existed
                    binding.updateEmail.setError("Email Already Exist");
                    //    binding.updateButton.setEnabled(false);
                }

            }).addOnFailureListener(Throwable::printStackTrace);
            return true;
        } else {
            return false;
        }
    }

    private boolean isPasswordChanged() {
        if (!user_password.equals(Objects.requireNonNull(binding.updatePassword).getText().toString())) {
            // binding.updateButton.setEnabled(true);
            databaseReference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                    .child("password").setValue(binding.updatePassword.getText().toString());
            user_password = binding.updatePassword.getText().toString();
            return true;
        } else return false;

    }

    private boolean isNameChanged() {
        if (!user_name.equals(Objects.requireNonNull(binding.updateUserName).getText().toString())) {
            //   binding.updateButton.setEnabled(true);
            databaseReference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                    .child("userName").setValue(binding.updateUserName.getText().toString());
            user_name = binding.updateUserName.getText().toString();
            return true;
        } else return false;
    }


}

