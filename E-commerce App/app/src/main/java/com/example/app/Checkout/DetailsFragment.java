package com.example.app.Checkout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.app.Model.AdapterData;
import com.example.app.Model.Addtocartmodel;
import com.example.app.Navdrawer.AddToCart;
import com.example.app.R;
import com.example.app.databinding.FragmentDetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class DetailsFragment extends Fragment {

    private  FragmentDetailsBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private AdapterData ad;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference1;
    private String price;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(getLayoutInflater());
        Bundle b=this.getArguments();
        ad = b.getParcelable("data");
        binding.detailsdataname.setText(ad.name);
        Glide.with(this).load(ad.url).override(600,600).into(binding.detailsdataimage);
        binding.detailsdataprice.setText(ad.price);
        binding.detailsdatadetails.setText(ad.details);
        binding.bookbutton.setOnClickListener(v->{
        });

        price = ad.price;
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = auth.getCurrentUser();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("Add To Cart");
        databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot.child(ad.id).exists()){
                            binding.addtocart.setVisibility(View.GONE);
                            binding.gotocart.setVisibility(View.VISIBLE);
                            binding.gotocart.setOnClickListener(v->{
                                checkdata();
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) { }
                });


        binding.addtocart.setOnClickListener(v->{

            Addtocartmodel addtocartmodel=new Addtocartmodel(ad.name,ad.price,ad.url,"1",ad.id,ad.price);

            databaseReference=FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("Add To Cart");


            databaseReference=FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("Add To Cart");

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if(snapshot.child(ad.id).exists()){

                            }
                            else{
                                databaseReference1=FirebaseDatabase.getInstance().getReference("users/"+user.getUid())
                                        .child("Add To Cart").child(ad.id);
                                databaseReference1.setValue(addtocartmodel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            showToast();
                                        }
                                    }
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) { }
                    });

//



        });

        binding.bookbutton.setOnClickListener(v->{
            CheckoutAddress cp=new CheckoutAddress();
            Bundle b=new Bundle();
            b.putString("data",price);
            cp.setArguments(b);

            FragmentTransaction ft= getFragmentManager().beginTransaction();
            ft.replace(R.id.placeholder,cp);
            ft.addToBackStack(null);
            ft.commit();

        });


    }

    public void showToast(){
        LayoutInflater inflater=getLayoutInflater();
        View layout=inflater.inflate(R.layout.custom_toast,getActivity().findViewById(R.id.custom_layout));
        Toast toast=new Toast(getContext());
        toast.setGravity(Gravity.BOTTOM,0,200);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

    }
    public void checkdata(){
        FragmentTransaction ft= getFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder,new AddToCart());
        ft.addToBackStack(null);
        ft.commit();


    }

}