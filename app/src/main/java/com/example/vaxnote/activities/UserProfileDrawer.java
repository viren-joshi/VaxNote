package com.example.vaxnote.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.vaxnote.R;
import com.example.vaxnote.classes.LocalInfo;
import com.example.vaxnote.fragments.AboutUsFragment;
import com.example.vaxnote.fragments.NewRecordFragment;
import com.example.vaxnote.fragments.UserProfileFragment;
import com.example.vaxnote.fragments.VaccineDictionary;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class UserProfileDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Variables
    static DrawerLayout drawerLayout;
    View header;
    NavigationView navigationView;
    Toolbar toolbar;
    int position;
    TextView nameUser;
    ActionBarDrawerToggle toggle;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_drawer);


        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.statusbar_yellow));


        toolbar = findViewById(R.id.toolbar3);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);


        toggle = new ActionBarDrawerToggle(UserProfileDrawer.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Intent intent = getIntent();
        String label = intent.getStringExtra("label");
        position = intent.getIntExtra("personPosition", 0);
        header = navigationView.getHeaderView(0);
        nameUser = (TextView) header.findViewById(R.id.nameUser);
        nameUser.setText(LocalInfo.getInstance(this).pList[position][1]);

        openUserProfileFragment(position);
        navigationView.setCheckedItem(R.id.userProfile);
        if(Objects.equals(label, "NewRecord")) {
            String name = intent.getStringExtra("name");
            String vaccine = intent.getStringExtra("vaccine");
            String doses = intent.getStringExtra("doses");
            openNewRecordFragment(name, vaccine, doses);
        }

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
        switch (menuitem.getItemId()) {
            case R.id.vac_dic:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new VaccineDictionary()).commit();
                break;

            case R.id.abt_us:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new AboutUsFragment()).commit();
                break;
            case R.id.home:
                startActivity(new Intent(this,MainMenuActivity.class));
                break;
            case R.id.userProfile:
                openUserProfileFragment(position);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openUserProfileFragment(int position){
        Bundle bundle = new Bundle();
        UserProfileFragment userProfileFragment = new UserProfileFragment();
        bundle.putInt("position",position);
        userProfileFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,userProfileFragment).commit();
    }

    public void openNewRecordFragment(String name,String vaccine,String doses){
        NewRecordFragment fragment = new NewRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        bundle.putString("vaccine",vaccine);
        bundle.putString("doses",doses);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment).commit();
    }

}

