package com.example.foodorderui.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.example.foodorderui.Adapter.UserAdapter;
import com.example.foodorderui.Domain.FoodDomain;
import com.example.foodorderui.Helper.Methods;
import com.example.foodorderui.MainActivity;
import com.example.foodorderui.Model.Fmodel;
import com.example.foodorderui.Model.UserModel;
import com.example.foodorderui.R;
import com.example.foodorderui.databinding.ActivitySettingBinding;
import com.example.foodorderui.databinding.BottomCardBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;


public class SettingActivity extends AppCompatActivity {
    ActivitySettingBinding binding;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    FirebaseAuth auth;
    FirebaseStorage storage;
    Uri file;
    boolean image = false;
    boolean chekName = false;
    ArrayList<UserModel> list = new ArrayList<>();

    public SettingActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Food");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("users");
       // database = FirebaseDatabase.getInstance();
        UserAdapter adapter = new UserAdapter(list, this);
        storage = FirebaseStorage.getInstance();

        binding.bottomCard.BottomHomeButton.setOnClickListener(v ->
                startActivity(new Intent(SettingActivity.this, MainActivity.class)));

        ShowAdminPanel(databaseReference2, binding.bottomCard, this);

        // change the color or userList and FoodList icon when switch position
        Methods.ChangeLoginSignUPColor(binding.users, binding.foods, binding.userListScrollView, binding.foodListScrollView, this);
        Methods.ChangeLoginSignUPColor(binding.foods, binding.users, binding.foodListScrollView, binding.userListScrollView, this);

        //   binding.ProfileImage.setOnClickListener(v -> image = v.equals(binding.ProfileImage));
       // save the new food
        binding.Save.setOnClickListener(v -> {
              // check that fields are null or not
            if (Methods.validateUserName(binding.editTextTextPersonName2) |
                    Methods.validateUserName(binding.editTextTextPersonName4) |
                    Methods.validateUserName(binding.editTextTextPersonName5) |
                    // !CheckProfileImage()
                    !image
            ) {
                Toast.makeText(this, "Fill all the field correctly", Toast.LENGTH_SHORT).show();
            } else {

                // call the save function if all field has data
                Save(file);
            }
        });
       // add the pic of food
        binding.AddPic.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            // for any type of file */*
            intent.setType("image/*");
            someActivityResultLauncher.launch(intent);
        });

        // show all the user in StaggeredGridLayoutManager
        binding.userList.setAdapter(adapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(
                3, StaggeredGridLayoutManager.VERTICAL);
        binding.userList.setLayoutManager(staggeredGridLayoutManager);
        // get all the user into the list
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    UserModel users = ds.getValue(UserModel.class);
                    Objects.requireNonNull(users).setUserId(ds.getKey());
                    // get all user instance current user
                    if (!users.getUserId().equals(FirebaseAuth.getInstance().getUid())) {
                        list.add(users);
                    }
                    /* Log.d("result", "User name: " + users.getUserName() + ", email " + users.getMail()); */
                }

                adapter.notifyDataSetChanged();

                // System.out.println("size of list is  " + list.size());
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // show all pizza
        Methods.changeOtherColor(binding.allPizza, binding.allBurger, binding.allHotDog, binding.allDrinks, binding.addFoods,
                binding.allDonats
            , binding.addPizzaLayout, binding.addBurgerLayout, binding.addHotDogLayout, binding.addDrinkLayout, binding.addFoodsLayout,
                binding.addDonateLayout , this);

        // show all burger

        Methods.changeOtherColor(binding.allBurger, binding.allPizza, binding.allHotDog, binding.allDrinks, binding.addFoods,
                binding.allDonats
                , binding.addBurgerLayout, binding.addPizzaLayout, binding.addHotDogLayout, binding.addDrinkLayout, binding.addFoodsLayout,
                binding.addDonateLayout, this);

        // show all hotDog

        Methods.changeOtherColor(binding.allHotDog, binding.allPizza, binding.allBurger, binding.allDrinks, binding.addFoods,
                binding.allDonats
                , binding.addHotDogLayout, binding.addPizzaLayout, binding.addBurgerLayout, binding.addDrinkLayout, binding.addFoodsLayout,
                binding.addDonateLayout, this);

        // show all drinks

        Methods.changeOtherColor(binding.allDrinks, binding.allPizza, binding.allHotDog, binding.allBurger, binding.addFoods,
                binding.allDonats
                , binding.addDrinkLayout, binding.addPizzaLayout, binding.addHotDogLayout, binding.addBurgerLayout, binding.addFoodsLayout,
                binding.addDonateLayout, this);

        // show all donate

        Methods.changeOtherColor(binding.allDonats, binding.allPizza, binding.allHotDog, binding.allDrinks, binding.addFoods,
                binding.allBurger
                , binding.addDonateLayout, binding.addPizzaLayout, binding.addHotDogLayout, binding.addDrinkLayout, binding.addFoodsLayout,
                binding.addBurgerLayout, this);

        // show addFoods
        Methods.changeOtherColor(binding.addFoods, binding.allPizza, binding.allHotDog, binding.allDrinks, binding.allDonats,
                binding.allBurger
                , binding.addFoodsLayout, binding.addPizzaLayout, binding.addHotDogLayout, binding.addDrinkLayout, binding.addDonateLayout,
                binding.addBurgerLayout, this);
    }



    // the save function that save the new food
    public void Save(Uri file) {

        FoodDomain foodDomain = new FoodDomain(
                binding.editTextTextPersonName2.getText().toString(),
                binding.editTextTextPersonName4.getText().toString(),
                parseDouble(binding.editTextTextPersonName5.getText().toString())
        );
        // save food details and pic at same time
        SaveInDB(foodDomain, file);
      //  CheckPic();
        //  times.accept(foodDomain, file);
    }
   // check the price is string or not
    private double parseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch (Exception e) {
                binding.editTextTextPersonName5.setError("Empty");
                return -1;// or some value to mark this field is wrong. or make a function validates field first ...
            }
        } else return 0.0;
    }
   // save the data in db
    private void SaveInDB(FoodDomain foodDomain, Uri file) {
        String id = binding.editTextTextPersonName2.getText().toString();
        // chek the name already exit or not
        // FoodDomain foodDomain1 = foodDomain;
        //  Uri file1 =  file;
        // if the name is already taken data can not be insert
        storage.getReference().child("Food/pizza").child(id).getDownloadUrl().addOnSuccessListener(uri ->
                Toast.makeText(this, "Name already exist", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(exception -> {
                    // File not found
                    chekName = true;
                });
    // finally the new food save
       databaseReference.child("pizza").child(binding.editTextTextPersonName2.getText().toString()).setValue(foodDomain);

        final StorageReference reference = storage.getReference().child("Food")
                .child("pizza").child(binding.editTextTextPersonName2.getText().toString());

        reference.putFile(file).addOnSuccessListener(taskSnapshot ->
                reference.getDownloadUrl().addOnSuccessListener(uri -> {
                    databaseReference.child("pizza").child(binding.editTextTextPersonName2.getText().toString())
                            .child("pic").setValue(uri.toString());


                    //  Toast.makeText(this, "Profile Picture Update", Toast.LENGTH_SHORT).show();
                }));



    }

    private void CheckPic() {
        Toast.makeText(this, "the " + chekName, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "insert", Toast.LENGTH_SHORT).show();

        // chek the name already exit or not
        // FoodDomain foodDomain1 = foodDomain;
        //  Uri file1 =  file;
        ArrayList<String> imageViews = new ArrayList<>();
        storage.getReference().child("Food/pizza").getDownloadUrl().addOnSuccessListener(uri -> {

       /*     for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                Fmodel foodDomain = dataSnapshot.getValue(Fmodel.class);
                String id = dataSnapshot.getValue(String.class);
                Toast.makeText(SettingActivity.this, id, Toast.LENGTH_SHORT).show();
                assert foodDomain != null;
                imageViews.add(foodDomain.getPic());
            }

            if (imageViews.contains(file.toString())) {
                Toast.makeText(SettingActivity.this, "done", Toast.LENGTH_SHORT).show();
            }

        */

        }).addOnFailureListener(exception -> {
            // File not found
            chekName = true;
        });

    }

 /// open pic from phone handle here
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();
                    assert data != null;
                    if (data.getData() != null) {
                        file = data.getData();

                        binding.ProfileImage.setImageURI(file);
                        image = true;
                        //   Save(file);

                    }
                }
            });

  // check that a user is admin or not
    public void ShowAdminPanel(DatabaseReference databaseReference, BottomCardBinding bottomCard, Activity activity) {

        databaseReference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild("admin")) {
                            //  String id = Objects.requireNonNull(snapshot.getValue()).toString();
                          //  binding.textView14.setVisibility(View.GONE);
                         //   binding.textView15.setVisibility(View.VISIBLE);

                        } else {

                          //  binding.textView14.setVisibility(View.VISIBLE);
                         //   binding.textView15.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}