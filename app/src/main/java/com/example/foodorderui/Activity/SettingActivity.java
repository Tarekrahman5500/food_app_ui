package com.example.foodorderui.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.example.foodorderui.Adapter.ShowFoodAdapter;
import com.example.foodorderui.Adapter.UserAdapter;
import com.example.foodorderui.Domain.FoodDomain;
import com.example.foodorderui.Helper.Methods;
import com.example.foodorderui.MainActivity;
import com.example.foodorderui.Model.UserModel;
import com.example.foodorderui.R;
import com.example.foodorderui.databinding.ActivitySettingBinding;
import com.example.foodorderui.databinding.BottomCardBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;


public class SettingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ActivitySettingBinding binding;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    FirebaseAuth auth;
    FirebaseStorage storage;
    Uri file;
    String foodName;
    boolean image = false;
    boolean chekName = false;
    ArrayList<UserModel> list = new ArrayList<>();
    ArrayList<FoodDomain> foodDomainArrayList = new ArrayList<>();


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
        ShowFoodAdapter showFoodAdapter = new ShowFoodAdapter(foodDomainArrayList, this);
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
                    Toast.makeText(SettingActivity.this, list.size(), Toast.LENGTH_SHORT).show();
                }

                adapter.notifyDataSetChanged();

                // System.out.println("size of list is  " + list.size());
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // show all food to admin
             getFromDB("pizza");
        int id = binding.allPizza.getId();
        String idStr = getResources().getResourceName(id);
        //Toast.makeText(this, getResources().getResourceEntryName(binding.allPizza.getId()), Toast.LENGTH_SHORT).show();

        // show all pizza
        binding.allPizza.setOnClickListener(v -> {
            Methods.changeOtherColor(binding.allPizza, binding.allBurger, binding.allHotDog, binding.allDrinks, binding.addFoods,
                    binding.allDonats
                    , binding.addPizzaLayout, binding.addBurgerLayout, binding.addHotDogLayout, binding.addDrinkLayout, binding.addFoodsLayout,
                    binding.addDonateLayout, this);
            getFromDB("pizza");
        });

        // show all burger

        binding.allBurger.setOnClickListener(v -> {
            Methods.changeOtherColor(binding.allBurger, binding.allPizza, binding.allHotDog, binding.allDrinks, binding.addFoods,
                    binding.allDonats
                    , binding.addBurgerLayout, binding.addPizzaLayout, binding.addHotDogLayout, binding.addDrinkLayout, binding.addFoodsLayout,
                    binding.addDonateLayout, this);

            getFromDB("burger");

        });


        // show all hotDog

        binding.allHotDog.setOnClickListener(v -> {
                    Methods.changeOtherColor(binding.allHotDog, binding.allPizza, binding.allBurger, binding.allDrinks, binding.addFoods,
                            binding.allDonats
                            , binding.addHotDogLayout, binding.addPizzaLayout, binding.addBurgerLayout, binding.addDrinkLayout, binding.addFoodsLayout,
                            binding.addDonateLayout, this);
                    getFromDB("hotdog");
                }
        );

        // show all drinks

        binding.allDrinks.setOnClickListener(v -> {
            Methods.changeOtherColor(binding.allDrinks, binding.allPizza, binding.allHotDog, binding.allBurger, binding.addFoods,
                    binding.allDonats
                    , binding.addDrinkLayout, binding.addPizzaLayout, binding.addHotDogLayout, binding.addBurgerLayout, binding.addFoodsLayout,
                    binding.addDonateLayout, this);
            getFromDB("drinks");
        });

        // show all donate

        binding.allDonats.setOnClickListener(v -> {
            Methods.changeOtherColor(binding.allDonats, binding.allPizza, binding.allHotDog, binding.allDrinks, binding.addFoods,
                    binding.allBurger
                    , binding.addDonateLayout, binding.addPizzaLayout, binding.addHotDogLayout, binding.addDrinkLayout, binding.addFoodsLayout,
                    binding.addBurgerLayout, this);
            getFromDB("donate");
        });

        // show addFoods
        binding.addFoods.setOnClickListener(v -> Methods.changeOtherColor(binding.addFoods, binding.allPizza, binding.allHotDog, binding.allDrinks, binding.allDonats,
                binding.allBurger
                , binding.addFoodsLayout, binding.addPizzaLayout, binding.addHotDogLayout, binding.addDrinkLayout, binding.addDonateLayout,
                binding.addBurgerLayout, this));



        // spinners when select add a food

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.listOfFood,
                android.R.layout.simple_spinner_item);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spinner.setAdapter(arrayAdapter);

        binding.spinner.setOnItemSelectedListener(this);
    }


    private void marge(ArrayList<FoodDomain> foodDomainArrayList1, String foodName) {

        //  Toast.makeText(SettingActivity.this, foodDomainArrayList1.size()+ " ", Toast.LENGTH_SHORT).show();
        RecyclerView Food;
        Food = binding.AllPizzaFood.addAllFoodListView;
        foodDomainArrayList = foodDomainArrayList1;
        // Toast.makeText(SettingActivity.this, foodDomainArrayList.get(0).getTitle()+ " main", Toast.LENGTH_SHORT).show();

        ShowFoodAdapter showFoodAdapter = new ShowFoodAdapter(foodDomainArrayList, this);

        if (foodName.equals("pizza")) Food = binding.AllPizzaFood.addAllFoodListView;
        if (foodName.equals("burger")) Food = binding.AllBurgerFood.addAllFoodListView;
        if (foodName.equals("hotdog")) Food = binding.AllHotDogFood.addAllFoodListView;
        if (foodName.equals("drinks")) Food = binding.AllDrinksFood.addAllFoodListView;
        if (foodName.equals("donate")) Food = binding.AllDonateFood.addAllFoodListView;

        Food.setAdapter(showFoodAdapter);

        StaggeredGridLayoutManager staggeredGridLayoutManager2 = new StaggeredGridLayoutManager(
                3, StaggeredGridLayoutManager.VERTICAL);
        Food.setLayoutManager(staggeredGridLayoutManager2);
    }

    // get all food from db
    public void getFromDB(String value) {

        ArrayList<FoodDomain> foodDomainArrayList1 = new ArrayList<>();
        databaseReference.child(value).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodDomainArrayList1.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    FoodDomain foodDomain = ds.getValue(FoodDomain.class);
                    //  Objects.requireNonNull(foodDomain).setUserId(ds.getKey());
                    foodDomainArrayList1.add(foodDomain);


                    /* Log.d("result", "User name: " + users.getUserName() + ", email " + users.getMail()); */
                }
                //  showFoodAdapter.notifyDataSetChanged();

                //   Toast.makeText(SettingActivity.this, foodDomainArrayList1.size()+ " ", Toast.LENGTH_SHORT).show();

                marge(foodDomainArrayList1, value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
        storage.getReference().child("Food").child(foodName).child(id).getDownloadUrl().addOnSuccessListener(uri ->
                Toast.makeText(this, "Name already exist", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(exception -> {
                    // File not found
                    chekName = true;
                });
        // finally the new food save
        databaseReference.child(foodName).child(binding.editTextTextPersonName2.getText().toString()).setValue(foodDomain);

        final StorageReference reference = storage.getReference().child("Food")
                .child(foodName).child(binding.editTextTextPersonName2.getText().toString());

        reference.putFile(file).addOnSuccessListener(taskSnapshot ->
                reference.getDownloadUrl().addOnSuccessListener(uri -> {
                    databaseReference.child(foodName).child(binding.editTextTextPersonName2.getText().toString())
                            .child("pic").setValue(uri.toString());


                    //  Toast.makeText(this, "Profile Picture Update", Toast.LENGTH_SHORT).show();
                }));


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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        foodName = parent.getItemAtPosition(position).toString();
        // Toast.makeText(parent.getContext(), string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}