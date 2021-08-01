package com.example.foodorderui.Fragments;

import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.foodorderui.R;
import com.example.foodorderui.databinding.FragmentBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;


public class BottomSheetFragment  extends BottomSheetDialogFragment {



    public BottomSheetFragment() {
        // Required empty public constructor
    }

    FragmentBottomSheetBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false);

        // get data from user adapter of a individual user
        Bundle mArgs = getArguments();
        assert mArgs != null;
        String name = mArgs.getString("name");
        String phone =  mArgs.getString("phone");
        String email =  mArgs.getString("email");
        String pass =  mArgs.getString("password");
        String picasso = mArgs.getString("pic");

       // show the current user data
        binding.showUserName.setText(name);
        binding.showuserPhone.setText(phone);
        binding.showUserPassword.setText(pass);
        binding.showUserEmail.setText(email);
        Picasso.get().load(picasso).placeholder(R.drawable.ic_user).into(binding.foodImage);
       return binding.getRoot();
    }
}