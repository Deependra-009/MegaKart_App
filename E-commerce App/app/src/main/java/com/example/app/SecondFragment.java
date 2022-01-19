package com.example.app;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.app.Model.UserRegistration;
import com.example.app.databinding.FragmentSecondBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String cpassword;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
    }
    

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        binding.registerbutton.setOnClickListener(v->{
            password = binding.registerpassword.getText().toString();
            email=binding.registeremail.getText().toString();
            name=binding.registername.getText().toString();
            phone=binding.registerphone.getText().toString();
            cpassword=binding.registercpassoword.getText().toString();
            if(name.length()==0){
                binding.registername.setError("Fill Details");
            }
            else if(email.length()==0){
                binding.registeremail.setError("Fill Details");
            }
            else if(phone.length()==0){
                binding.registerphone.setError("Fill Details");
            }
            else if(password.length()==0){
                binding.registerpassword.setError("Fill Details");
            }
            else if(cpassword.length()==0){
                binding.registercpassoword.setError("Fill Details");
            }
            else{
                if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    if(Patterns.PHONE.matcher(phone).matches()){
                        if(password.equals(cpassword)){
                            auth.createUserWithEmailAndPassword(email,password)
                                    .addOnCompleteListener(getActivity(),new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getContext(),"user registered",Toast.LENGTH_SHORT).show();
                                                UserRegistration userRegistration=new UserRegistration(name,email,phone,password);
                                                FirebaseDatabase.getInstance().getReference("users")
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                        .setValue(userRegistration).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(getContext(),"userdata registered",Toast.LENGTH_SHORT).show();
                                                            NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
                                                        }
                                                        else{
                                                            Toast.makeText(getContext(),"data not registered",Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });


                                            }
                                            else{
                                                Toast.makeText(getContext(),"registration failed",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else{
                            binding.registerpassword.setError("Password & Confirm Password Should be Equal");
                        }
                    }
                    else{
                        binding.registerphone.setError("Invalid Phone Number");
                    }
                }
                else{
                    binding.registeremail.setError("Invalid Email");
                }
            }
        });
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        -------> Set Image in Background <-----------

        ConstraintLayout c=(ConstraintLayout)binding.registerlayout;
        Glide.with(this).load(R.drawable.image4).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull @NotNull Drawable resource, @Nullable @org.jetbrains.annotations.Nullable Transition<? super Drawable> transition) {
                c.setBackground(resource);
            }
        });
   }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}