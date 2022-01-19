package com.example.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.app.Checkout.CheckoutAddress;
import com.example.app.Model.Userdata;
import com.example.app.Navdrawer.AddToCart;
import com.example.app.Navdrawer.AllCategories;
import com.example.app.Navdrawer.Appliances;
import com.example.app.Navdrawer.Electronics;
import com.example.app.Navdrawer.Fashion;
import com.example.app.Navdrawer.Home;
import com.example.app.Navdrawer.HomeDecor;
import com.example.app.Navdrawer.Mobiles;
import com.example.app.Navdrawer.Profile;
import com.example.app.userapi.APILoader;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NavDrawer extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<String>{

    DrawerLayout drawerLayout;
    private FirebaseAuth auth;
    public static String UID=null;
//    --------> API DATA <----------
    NavigationView nvdrawer;
    String listname[]={"mobile","electronics","homedecor","fashion","appliances"};
    ArrayList<JSONObject> list=new ArrayList<>();
    HashMap<String,ArrayList<JSONObject>> hashMap=new HashMap<>();
    public static ArrayList<JSONObject> MobileList=new ArrayList<>();
    public static ArrayList<JSONObject> ElectronicsList=new ArrayList<>();
    public static ArrayList<JSONObject> HomeDecorList=new ArrayList<>();
    public static ArrayList<JSONObject> AppliancesList=new ArrayList<>();
    public static ArrayList<JSONObject> FashionList=new ArrayList<>();
    public String API_DATA;
    private DatabaseReference databaseReference;
    public static List<Userdata> userdataList;

    @Override
    protected void onStart() {
        super.onStart();
        ExtractData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_nav_drawer);
        drawerLayout=findViewById(R.id.drawer_layout);

        TextView homeusername=findViewById(R.id.appusername);
        FirebaseUser user=auth.getCurrentUser();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        userdataList=new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String name=snapshot.child("name").getValue().toString();
                String email=snapshot.child("email").getValue().toString();
                String phone=snapshot.child("phone").getValue().toString();
                Userdata userdata=new Userdata(name,email,phone);
                userdataList.add(userdata);
                homeusername.setText(userdataList.get(0).name);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });




        FragmentTransaction fm=getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.placeholder,new AllCategories());
        fm.addToBackStack(null);
        fm.commit();
        closerDrawer(drawerLayout);
        UID=auth.getUid();


        if(getSupportLoaderManager().getLoader(0)!=null){
            getSupportLoaderManager().initLoader(0,null,this);
        }


    }

    /*************************************************************************************************/
    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }
    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void ClickLogo(View view){
        FragmentTransaction fm=getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.placeholder,new Home());
        fm.addToBackStack(null);
        fm.commit();
        closerDrawer(drawerLayout);

        closerDrawer(drawerLayout);
    }
    public static  void closerDrawer(DrawerLayout drawerLayout) {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    /************************************** Set Data of API *******************************************************************/
    public void GetData(){
        try{
            JSONObject jsonObject=new JSONObject(API_DATA);
            for(int i=0;i<listname.length;i++){
                list=new ArrayList<>();
                JSONArray data_array=jsonObject.getJSONArray(listname[i]);
                for(int j=0;j<data_array.length();j++){
                    list.add(data_array.getJSONObject(j));
                }
                hashMap.put(listname[i],list);
            }
        }
        catch(Exception e){
            Log.e("Error",e.getMessage());
        }

        MobileList=hashMap.get("mobile");
        ElectronicsList=hashMap.get("electronics");
        HomeDecorList=hashMap.get("homedecor");
        FashionList=hashMap.get("fashion");
        AppliancesList=hashMap.get("appliances");
    }

    public void ExtractData(){
        Bundle b=new Bundle();
        getSupportLoaderManager().restartLoader(0,b,this);
    }
    @NonNull
    @NotNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable @org.jetbrains.annotations.Nullable Bundle args) {
        return new APILoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull @NotNull Loader<String> loader, String data) {
        try{
            API a=new API();
            API_DATA=a.data;
            GetData();
        }
        catch(Exception e){
            Log.e("Error",e.getMessage());
        }
    }

    @Override
    public void onLoaderReset(@NonNull @NotNull Loader<String> loader) {

    }
    /*************************************** Mobile **********************************************************/

    public void clickmobiles(View view){

        FragmentTransaction fm=getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.placeholder,new Mobiles());
        fm.addToBackStack(null);
        fm.commit();
        closerDrawer(drawerLayout);


    }
    @Override
    protected void onPause() {
        super.onPause();
        closerDrawer(drawerLayout);
    }

    /*************************************** Electronics **********************************************************/

    public void clickelectronic(View view){
        FragmentTransaction fm=getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.placeholder,new Electronics());
        fm.addToBackStack(null);
        fm.commit();
        closerDrawer(drawerLayout);


    }

    /*************************************** Home Decor **********************************************************/

    public void clickhomedecor(View view){
        FragmentTransaction fm=getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.placeholder,new HomeDecor());
        fm.addToBackStack(null);
        fm.commit();
        closerDrawer(drawerLayout);


    }

    /*************************************** Fashion **********************************************************/

    public void clickfashion(View view){
        FragmentTransaction fm=getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.placeholder,new Fashion());
        fm.addToBackStack(null);
        fm.commit();
        closerDrawer(drawerLayout);


    }

    /*************************************** Appliances **********************************************************/

    public void clickappliances(View view){

        FragmentTransaction fm=getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.placeholder,new Appliances());
        fm.addToBackStack(null);
        fm.commit();
        closerDrawer(drawerLayout);


    }

    /*************************************** Grocery **********************************************************/

    public void clickgrocery(View view){
        closerDrawer(drawerLayout);

    }
    /*************************************** All Categories **********************************************************/

    public void clickallcategories(View view){
        FragmentTransaction fm=getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.placeholder,new AllCategories());
        fm.addToBackStack(null);
        fm.commit();
        closerDrawer(drawerLayout);
    }

    /*************************************** Grocery **********************************************************/

    public void clicklogout(View view){

        auth.signOut();

        Intent i=new Intent(NavDrawer.this, MainActivity.class);
        startActivity(i);

    }
    /*************************************** My Account **********************************************************/

    public void clickmyaccount(View view){
        FragmentTransaction fm=getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.placeholder,new Profile());
        fm.addToBackStack(null);
        fm.commit();
        closerDrawer(drawerLayout);
    }
    /*************************************** Add To Cart **********************************************************/


    public void ClickAddtocart(View view){
        FragmentTransaction fm=getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.placeholder,new AddToCart());
        fm.addToBackStack(null);
        fm.commit();
        closerDrawer(drawerLayout);
    }



}