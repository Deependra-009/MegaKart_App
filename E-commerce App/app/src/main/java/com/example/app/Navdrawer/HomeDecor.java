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

import com.bumptech.glide.Glide;
import com.example.app.Checkout.DetailsFragment;
import com.example.app.Model.AdapterData;
import com.example.app.NavDrawer;
import com.example.app.R;
import com.example.app.databinding.FragmentHomeDecorBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeDecor extends Fragment {


    private  FragmentHomeDecorBinding bind;
    ArrayList<AdapterData> homedecorlist=new ArrayList<>();
    HomeDecorAdapter homeDecorAdpater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bind = FragmentHomeDecorBinding.inflate(getLayoutInflater());
        return bind.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try{
            for(int i = 0; i< NavDrawer.HomeDecorList.size(); i++){
                JSONObject obj=NavDrawer.HomeDecorList.get(i);
                JSONArray array=obj.getJSONArray("list");
                homedecorlist.add(new AdapterData(obj.getString("name"),obj.getString("price"),array.getJSONObject(0).getString("l"),obj.getString("image"),obj.getString("id")));
            }
        }
        catch(Exception e){

        }



        bind.homedecorrecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeDecorAdpater=new HomeDecorAdapter(getActivity(),  homedecorlist,R.layout.row_mobile_layout);
        bind.homedecorrecycler.setAdapter(homeDecorAdpater);
        bind.homedecorrecycler.getAdapter().notifyDataSetChanged();


    }

    public void   GoToDetailsPage(AdapterData data){
        DetailsFragment df=new DetailsFragment();
        Bundle b=new Bundle();
        b.putParcelable("data",data);
        df.setArguments(b);

        FragmentTransaction fm=getFragmentManager().beginTransaction();
        fm.replace(R.id.placeholder,df);
        fm.addToBackStack(null);
        fm.commit();


    }

    class HomeDecorAdapter extends RecyclerView.Adapter<HomeDecorAdapter.Holder>{
        Context ctx;
        List<AdapterData> list;
        int layout;
        LayoutInflater inflater;

        public HomeDecorAdapter(Context ctx, List<AdapterData> list, int layout) {
            this.ctx = ctx;
            this.list = list;
            this.layout = layout;
            inflater=LayoutInflater.from(ctx);
        }

        @NonNull
        @NotNull
        @Override
        public Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            View v=inflater.inflate(layout,parent,false);
            return new HomeDecor.HomeDecorAdapter.Holder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull HomeDecorAdapter.Holder holder, int position) {
            AdapterData adapterData=list.get(position);
            holder.bind(adapterData);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class Holder extends RecyclerView.ViewHolder {
            TextView name;
            TextView price;
            TextView details;
            ImageView img;

            public Holder(@NonNull @NotNull View itemView) {
                super(itemView);
                name=itemView.findViewById(R.id.dataname);
                price=itemView.findViewById(R.id.dataprice);
                details=itemView.findViewById(R.id.datadetail);
                img=itemView.findViewById(R.id.dataimage);
                itemView.setOnClickListener(v->{
                    AdapterData adapterData=list.get(getAdapterPosition());
                    GoToDetailsPage(adapterData);

                });

            }
            public void bind(AdapterData adapterData){
                name.setText(adapterData.name);
                price.setText(adapterData.price);
                details.setText(adapterData.details);
                Glide.with(ctx).load(adapterData.url).override(600,600).into(img);
            }
        }
    }
}