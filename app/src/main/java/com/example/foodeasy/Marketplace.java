package com.example.foodeasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Marketplace extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketplace);

        getSupportActionBar().hide();

        bottomNavigationView=findViewById(R.id.bottom_nav_view);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();

                if(id==R.id.home_nav){
                    Home_Fragment home_fragment=new Home_Fragment();
                    loadFragment(home_fragment,false);
                }
                else if (id==R.id.cart_nav) {
                    Cart_Fragment cart_fragment=new Cart_Fragment();
                    loadFragment(cart_fragment,false);
                }
                else if (id==R.id.history_nav) {
                    History_Fragment history_fragment=new History_Fragment();
                    loadFragment(history_fragment,false);
                }
                else if (id==R.id.profile_nav) {
                    Profile_Fragment profile_fragment=new Profile_Fragment();
                    loadFragment(profile_fragment,false);
                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.home_nav);
    }

    public  void loadFragment(Fragment fragment,boolean flag){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        if(flag){
            ft.add(R.id.container,fragment);
        }
        else{
            ft.replace(R.id.container,fragment);
        }

        ft.commit();
    }

}