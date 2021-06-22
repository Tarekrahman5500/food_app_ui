package com.example.foodorderui.Helper;

import android.widget.EditText;
import androidx.annotation.NonNull;
import com.example.foodorderui.Model.UserModel;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.Objects;

public class DatabaseMethods {

   static int countPhn = 0;
    public int CheckPhoneDuplicate(EditText userPhoneNumber) {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ArrayList<UserModel> list = new ArrayList<>();
        ArrayList<String> stringArrayList = new ArrayList<>();

        mDatabase.child("users").addValueEventListener(new ValueEventListener() {

            @Override
            public  void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    UserModel users = ds.getValue(UserModel.class);
                  //  String id =
                    Objects.requireNonNull(users).setUserId(ds.getKey());
                    list.add(users);

                }
                System.out.println("list size "+ list.size());
                for (UserModel elements : list) {
                    //int count =0;
                    if (elements.getPhone().equals(userPhoneNumber.getText().toString())) {
                        System.out.println(elements.getPhone());
                        userPhoneNumber.setError("Phone Number Already Exist");
                        System.out.println(userPhoneNumber.getText().toString());
                        stringArrayList.add(elements.getPhone());
                        //  countPhn = countPhn + 1;
                    }

                }
                System.out.println("size of list is  now " + list.size());
                System.out.println("phn " +stringArrayList.size());

               // System.out.println("after match "+countPhn);

            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
         // System.out.println("size of list is  now " + list.size());



      //  System.out.println("flag "+ countPhn);
        System.out.println("main size "+stringArrayList.size());
      return stringArrayList.size();
    }
}

