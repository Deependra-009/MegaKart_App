package com.example.app.Checkout;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.app.Navdrawer.Home;
import com.example.app.R;
import com.example.app.databinding.FragmentCheckoutSuccessBinding;

import org.jetbrains.annotations.NotNull;

public class CheckoutSuccess extends Fragment {


    private  FragmentCheckoutSuccessBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCheckoutSuccessBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.progressBaradd3.setProgress(100);
        binding.progressBaradd3.getProgressDrawable().setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);

//        ----------->  Automatic move to Home Page after 2 Sec <--------------

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Home home=new Home();
                FragmentTransaction fm=getFragmentManager().beginTransaction();
                fm.replace(R.id.placeholder,home);
                fm.addToBackStack(null);
                fm.commit();
            }
        };

        handler.postDelayed(runnable, 2000);



    }
}