package com.example.app.Navdrawer;

import android.content.Context;
import android.content.res.TypedArray;
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

import com.bumptech.glide.Glide;
import com.example.app.Model.AllCategoriesData;
import com.example.app.R;
import com.example.app.databinding.FragmentAllCategoriesBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class AllCategories extends Fragment {

    private FragmentAllCategoriesBinding binding;
    private ArrayList<AllCategoriesData> mdata;
    private AllCategoriesAdapter allCategoriesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAllCategoriesBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mdata= new ArrayList<>();
        CharSequence[] titlearray=getActivity().getResources().getTextArray(R.array.allcategoriesname);
        CharSequence[] discarray=getActivity().getResources().getTextArray(R.array.allcategoriesdisc);
        TypedArray imgarray=getActivity().getResources().obtainTypedArray(R.array.allcategoriesimages);

        for(int i=0;i<titlearray.length;i++){
            mdata.add(new AllCategoriesData(titlearray[i],discarray[i],imgarray.getResourceId(i,0)));
        }
        binding.allcategoriesrecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        allCategoriesAdapter = new AllCategoriesAdapter(getActivity(),mdata,R.layout.row_all_categories_layout);
        binding.allcategoriesrecycler.setAdapter(allCategoriesAdapter);
        imgarray.recycle();


    }

    public void GoToNext(int position){
        FragmentTransaction ft=getFragmentManager().beginTransaction();
        switch (position){
            case 0:ft.replace(R.id.placeholder,new Mobiles());
                   break;
            case 1:ft.replace(R.id.placeholder,new Electronics());
                   break;
            case 2:ft.replace(R.id.placeholder,new HomeDecor());
                   break;
            case 3:ft.replace(R.id.placeholder,new Fashion());
                   break;
            case 4:ft.replace(R.id.placeholder,new Appliances());
                   break;
            default:ft.replace(R.id.placeholder,new Home());
        }
        ft.addToBackStack(null);
        ft.commit();
    }

    public class AllCategoriesAdapter extends RecyclerView.Adapter<AllCategoriesAdapter.Holder>{

        Context ctx;
        List<AllCategoriesData> list;
        int layout;
        LayoutInflater inflater;

        public AllCategoriesAdapter(Context ctx, List<AllCategoriesData> list, int layout) {
            this.ctx = ctx;
            this.list = list;
            this.layout = layout;
            inflater=LayoutInflater.from(ctx);
        }

        @NonNull
        @NotNull
        @Override
        public AllCategoriesAdapter.Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            View v=inflater.inflate(layout,parent,false);
            return new AllCategories.AllCategoriesAdapter.Holder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull AllCategoriesAdapter.Holder holder, int position) {
            AllCategoriesData allCategoriesData=list.get(position);
            holder.bindto(allCategoriesData);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class Holder extends RecyclerView.ViewHolder {
            TextView name;
            ImageView img;
            TextView disc;
            public Holder(@NonNull @NotNull View itemView) {
                super(itemView);
                name=itemView.findViewById(R.id.categoryname);
                img=itemView.findViewById(R.id.categoryimage);
                disc=itemView.findViewById(R.id.categorydisc);
                itemView.setOnClickListener(v->{
                    GoToNext(getAdapterPosition());
                });

            }
            public void bindto(AllCategoriesData allCategoriesData){
                name.setText(allCategoriesData.title);
                disc.setText(allCategoriesData.disc);
                Glide.with(ctx).load(allCategoriesData.image).override(600,600).into(img);
            }
        }
    }






}