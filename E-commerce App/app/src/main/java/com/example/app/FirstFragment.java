package com.example.app;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import com.example.app.databinding.FragmentFirstBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private FirebaseAuth auth;
    private String email;
    private String password;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        auth=FirebaseAuth.getInstance();
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        binding.loginbutton.setOnClickListener(v->{
            email=binding.loginemail.getText().toString();
            password=binding.loginpassword.getText().toString();
            if(email.length()==0){
                binding.loginemail.setError("Fill Details");
            }
            else if(password.length()==0){
                binding.loginpassword.setError("Fill Details");
            }
            else{
                if(password.length()<6){
                    binding.loginpassword.setError("Password should be greater than 6 Character");
                }
                else{
                    if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        auth.signInWithEmailAndPassword(email,password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            Intent i=new Intent(getActivity(),NavDrawer.class);
//
                                            startActivity(i);
                                            Toast.makeText(getContext(),"Valid E-mail and Password",Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(getContext(),"Invalid E-mail and Password",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    else{
                        binding.loginemail.setError("Invalid Email");
                    }
                }
            }
        });

        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        ConstraintLayout c=(ConstraintLayout)binding.loginlayout;
        Glide.with(this).load(R.drawable.image2).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull @NotNull Drawable resource, @Nullable @org.jetbrains.annotations.Nullable Transition<? super Drawable> transition) {
                c.setBackground(resource);
            }
        });

        super.onViewCreated(view, savedInstanceState);
        binding.loginregister.setOnClickListener(v->{
            NavHostFragment.findNavController(this).navigate(R.id.action_FirstFragment_to_SecondFragment);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user= auth.getCurrentUser();
        if(user!=null){
            Intent i=new Intent(getActivity(),NavDrawer.class);
            startActivity(i);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}