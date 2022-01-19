package com.example.app.Navdrawer;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.app.Checkout.CheckoutAddress;
import com.example.app.Model.Addtocartmodel;
import com.example.app.R;
import com.example.app.databinding.FragmentAddToCartBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class AddToCart extends Fragment {

    private FirebaseAuth auth;
    private FragmentAddToCartBinding binding;
    private FirebaseUser user;
    private Addtocartadapter addtocartadapter;
    private List<Addtocartmodel> list;
    private DatabaseReference databaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth=FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddToCartBinding.inflate(getLayoutInflater());
        getdata();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.placeorder.setOnClickListener(v -> {
            CheckoutAddress cp=new CheckoutAddress();
            Bundle b=new Bundle();
            b.putCharSequence("data",binding.cartprice.getText());
            cp.setArguments(b);
            FragmentTransaction ft= getFragmentManager().beginTransaction();
            ft.replace(R.id.placeholder,cp);
            ft.addToBackStack(null);
            ft.commit();

        });


        binding.noproductbtn.setOnClickListener(v->{
            FragmentTransaction ft= getFragmentManager().beginTransaction();
            ft.replace(R.id.placeholder,new AllCategories());
            ft.addToBackStack(null);
            ft.commit();
        });










    }
    public void getdata(){
        list = new ArrayList<>();

        binding.addrecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        addtocartadapter = new Addtocartadapter(getActivity(), list,R.layout.row_add_to_cart);
        binding.addrecycler.setAdapter(addtocartadapter);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("Add To Cart");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();

                int price=0;
                for(DataSnapshot data:snapshot.getChildren()){
                    String tempprice=null;

                    Addtocartmodel addtocartmodel=data.getValue(Addtocartmodel.class);
                    tempprice=addtocartmodel.newprice.replace(",","").replace(" ","").replace("₹","");
                    price=price+Integer.parseInt(tempprice);
                    list.add(addtocartmodel);
                }
                binding.cartprogressbar.setVisibility(View.GONE);
                if(list.size()==0){
                    binding.noproductfound.setVisibility(View.VISIBLE);
                    binding.noproductbtn.setVisibility(View.VISIBLE);
                    binding.constraintLayout2.setVisibility(View.GONE);
                }
                binding.addrecycler.getAdapter().notifyDataSetChanged();
                String tempprice=String.valueOf(price);
                binding.cartprice.setText(new StringBuilder("₹ "+tempprice));

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    class Addtocartadapter extends RecyclerView.Adapter<Addtocartadapter.Holder>{

        Context ctx;
        List<Addtocartmodel> addtocartmodelListlist;
        int layout;
        LayoutInflater inflater;

        public Addtocartadapter(Context ctx, List<Addtocartmodel> addtocartmodelListlist1, int layout) {
            this.ctx = ctx;
            this.addtocartmodelListlist = addtocartmodelListlist1;
            this.layout = layout;
            inflater=LayoutInflater.from(ctx);
        }



        @NonNull
        @NotNull
        @Override
        public Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            View v=inflater.inflate(layout,parent,false);
            return new AddToCart.Addtocartadapter.Holder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull Addtocartadapter.Holder holder, int position) {
            Addtocartmodel addtocartmodel=addtocartmodelListlist.get(position);
            holder.bind(addtocartmodel);
        }

        @Override
        public int getItemCount() {
            return addtocartmodelListlist.size();
        }

        public class Holder extends RecyclerView.ViewHolder {

            TextView name;
            TextView price;
            TextView quantity;
            ImageView img;
            int newquantity=0;
            Long newprice,oldprice;


            public Holder(@NonNull @NotNull View itemView) {
                super(itemView);
                name=itemView.findViewById(R.id.atcname);
                price=itemView.findViewById(R.id.atcprice);
                quantity=itemView.findViewById(R.id.atcquantity);
                img=itemView.findViewById(R.id.atcimage);

                /******************** Increase quantity ************************************************/

                ImageView increase=itemView.findViewById(R.id.atcincreasebtn);
                increase.setOnClickListener(v->{
                    newquantity=Integer.parseInt(addtocartmodelListlist.get(getAdapterPosition()).quantity)+1;


                    newprice=Long.parseLong(addtocartmodelListlist.get(getAdapterPosition()).newprice
                            .replace(",","")
                            .replace(" ","")
                            .replace("₹",""))+Long.parseLong(addtocartmodelListlist.get(getAdapterPosition()).price
                            .replace(",","")
                            .replace(" ","")
                            .replace("₹",""));


                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference()
                            .child("users")
                            .child(user.getUid())
                            .child("Add To Cart")
                            .child(addtocartmodelListlist.get(getAdapterPosition()).id);
                    databaseReference.child("quantity").setValue(String.valueOf(newquantity));
                    databaseReference.child("newprice").setValue(String.valueOf(newprice));
                    quantity.setText(String.valueOf(newquantity));
                    price.setText(String.valueOf(newprice));
                });

                /******************** Decrease quantity ************************************************/

                ImageView decrease=itemView.findViewById(R.id.atcdecreasebtn);
                decrease.setOnClickListener(v->{
                    int temp=Integer.parseInt(addtocartmodelListlist.get(getAdapterPosition()).quantity);
                    if(temp>1){
                        newquantity=temp-1;

                        newprice=Long.parseLong(addtocartmodelListlist.get(getAdapterPosition()).newprice
                                .replace(",","")
                                .replace(" ","")
                                .replace("₹",""))-Long.parseLong(addtocartmodelListlist.get(getAdapterPosition()).price
                                .replace(",","")
                                .replace(" ","")
                                .replace("₹",""));



                        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference()
                                .child("users")
                                .child(user.getUid())
                                .child("Add To Cart")
                                .child(addtocartmodelListlist.get(getAdapterPosition()).id);
                        databaseReference.child("quantity").setValue(String.valueOf(newquantity));
                        databaseReference.child("newprice").setValue(String.valueOf(newprice));
                        quantity.setText(String.valueOf(newquantity));
                        price.setText(String.valueOf(newprice));
                    }
                });

                /******************** delete product ************************************************/


                ImageView deleteproduct=itemView.findViewById(R.id.atcdeletebtn);
                deleteproduct.setOnClickListener(v-> {
                    int pos=getAdapterPosition();
                    databaseReference = FirebaseDatabase.getInstance().getReference()
                            .child("users")
                            .child(user.getUid())
                            .child("Add To Cart")
                            .child(addtocartmodelListlist.get(getAdapterPosition()).id);

                    databaseReference.removeValue();
                    addtocartmodelListlist.remove(pos);
                    notifyItemRemoved(pos);


//
                });

//




            }
            public void bind(Addtocartmodel addtocartmodel){
                name.setText(addtocartmodel.name);
                price.setText(addtocartmodel.newprice);
                quantity.setText(addtocartmodel.quantity);
                Glide.with(ctx).load(addtocartmodel.url).override(600,600).into(img);
            }
        }
    }

}