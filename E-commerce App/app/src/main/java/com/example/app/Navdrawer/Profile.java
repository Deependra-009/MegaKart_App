package com.example.app.Navdrawer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app.NavDrawer;
import com.example.app.databinding.FragmentProfileBinding;

import org.jetbrains.annotations.NotNull;

public class Profile extends Fragment {


    private FragmentProfileBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.profileemail.setMovementMethod(new ScrollingMovementMethod());
        binding.profilename.setText(NavDrawer.userdataList.get(0).name);
        binding.profileemail.setText(NavDrawer.userdataList.get(0).email);
        binding.profilephone.setText(NavDrawer.userdataList.get(0).phone);



    }
}