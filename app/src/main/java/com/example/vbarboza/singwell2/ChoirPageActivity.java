package com.example.vbarboza.singwell2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ChoirPageActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener  {

    Toolbar mToolbar;
    SharedPreferences sharedPreferences;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choir_page);

        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);

        //If user id is not a number, no user is logged in, redirect to LoginActivity
        if (sharedPreferences.getString("id", "id").toString() == "id"){
            System.out.println("id: " + sharedPreferences.getString("id", "id").toString());
            startActivity(new Intent(this, LoginActivity.class));
        }

        //REMOVE, for debug purpose only
        System.out.println("******************INSIDE CHOIR PAGE ACTIVITY*************");

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Choir Page");
        setSupportActionBar(mToolbar);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        ImageView btn = findViewById(R.id.action3);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRosterActivity(v);
            }
        });

    }

    public void goToRosterActivity (View view){
        Intent intent = new Intent (this, RosterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, HomeActivity.class));

                break;
            case 1:
                startActivity(new Intent(this, ChoirListLActivity.class));

                break;
            case 2:
                startActivity(new Intent(this, ProfileActivity.class));

                break;
            case 3:
                startActivity(new Intent(this, LoginActivity.class));

                break;
            case 4:
                startActivity(new Intent(this, RegisterActivity.class));

                break;
            case 5:
                startActivity(new Intent(this, ChoirPageActivity.class));

                break;
            default:
                break;
        }


    }

}
