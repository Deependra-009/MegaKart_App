package com.example.app.Checkout;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app.R;
import com.example.app.databinding.FragmentCheckoutAddressBinding;
import com.example.app.databinding.FragmentCheckoutPaymentBinding;

import org.jetbrains.annotations.NotNull;


public class CheckoutPayment extends Fragment {

    private FragmentCheckoutPaymentBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentCheckoutPaymentBinding.inflate(getLayoutInflater());
        Bundle b=this.getArguments();
        String price=b.getString("data");

        binding.loginemail7.setText(price);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.progressBaradd2.setProgress(50);
        binding.progressBaradd2.getProgressDrawable().setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);
        binding.registerbutton3.setOnClickListener(v -> {
            CheckoutSuccess cs=new CheckoutSuccess();
            FragmentTransaction fm=getFragmentManager().beginTransaction();
            fm.replace(R.id.placeholder,cs);
            fm.addToBackStack(null);
            fm.commit();
        });
    }
}