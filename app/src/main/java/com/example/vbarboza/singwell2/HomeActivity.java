package com.example.vbarboza.singwell2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;



public class HomeActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);



    }


    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                startActivity(new Intent(this, HomeActivity.class));
                title = getString(R.string.title_home);
                break;
            case 1:
                startActivity(new Intent(this, ChoirListLActivity.class));
                title = getString(R.string.title_choirs);
                break;
            case 2:
                startActivity(new Intent(this, ProfileActivity.class));
                title = getString(R.string.title_profile);
                break;
            case 3:
                //Intent startLoginActivity = new Intent(this, LoginActivity.class);
                startActivity(new Intent(this, LoginActivity.class));
                //startActivity(new Intent(this, LoginActivity.class));
                title = getString(R.string.title_login);
                break;
            case 4:
                startActivity(new Intent(this, RegisterActivity.class));
                title = getString(R.string.title_Register);
                break;
            default:
                break;
        }

        // set the toolbar title
        getSupportActionBar().setTitle(title);

    }
}
