package com.example.app.Checkout;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app.NavDrawer;
import com.example.app.R;
import com.example.app.databinding.FragmentCheckoutAddressBinding;

import org.jetbrains.annotations.NotNull;


public class CheckoutAddress extends Fragment {


    private FragmentCheckoutAddressBinding binding;
    private String price;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCheckoutAddressBinding.inflate(getLayoutInflater());
        Bundle b=this.getArguments();
        price = b.getString("data");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.progressBaradd.setProgress(0);
        binding.loginemail2.setText(NavDrawer.userdataList.get(0).name);
        binding.loginemail3.setText(NavDrawer.userdataList.get(0).phone);
        binding.progressBaradd.getProgressDrawable().setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);
        binding.registerbutton2.setOnClickListener(v -> {
            CheckoutPayment cp=new CheckoutPayment();
            Bundle b=new Bundle();
            b.putString("data",price);
            cp.setArguments(b);

            FragmentTransaction fm=getFragmentManager().beginTransaction();
            fm.replace(R.id.placeholder,cp);
            fm.addToBackStack(null);
            fm.commit();
        });

    }
}