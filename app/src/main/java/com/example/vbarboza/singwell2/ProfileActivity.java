package com.example.vbarboza.singwell2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;


public class ProfileActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    TextView textViewName, textViewLastName, textViewCellNumber, textViewEmail, textViewUsername, textViewId;
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

//        //if user is not logged in, start the login activity
//        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
//            finish();
//            startActivity(new Intent(this, LoginActivity.class));
//        }


//        textViewId = findViewById(R.id.textViewId);
//        textViewUsername = findViewById(R.id.textViewUsername);
//        textViewEmail = findViewById(R.id.textViewEmail);
//
//        //getting current user
//        User user = SharedPrefManager.getInstance(this).getUser();
//
//        //setting the values to the textviews
//        textViewId.setText(String.valueOf(user.getId()));
//        textViewUsername.setText(user.getUsername());
//        textViewEmail.setText(user.getEmail());

        //when the user presses logout button
        //calling the logout method
//        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//                SharedPrefManager.getInstance(getApplicationContext()).logout();
//            }
//        });
    }





    //@Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.activity_profile, container, false);
//
//
//        // Inflate the layout for this fragment
//        return rootView;
//    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);

    }
    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new ChoirListLFragment();
                title = getString(R.string.title_choirs);
                break;
            case 2:
                startActivity(new Intent(this, ProfileActivity.class));
                title = getString(R.string.title_profile);
                break;
            case 3:
                Intent startLoginActivity = new Intent(this, LoginActivity.class);
                startActivity(startLoginActivity);
                title = getString(R.string.title_login);
                //startActivity(new Intent(this, LoginActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, RegisterActivity.class));
                title = getString(R.string.title_Register);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
}
