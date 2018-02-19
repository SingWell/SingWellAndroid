package com.example.vbarboza.singwell2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

public class ProfileActivity extends AppCompatActivity {

    TextView textViewName, textViewLastName, textViewCellNumber, textViewEmail, textViewFullName, textViewId;
    Button buttonLogout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        System.out.println("******************INSIDE PROFILE ACTIVITY*************");
        Toolbar toolbar = findViewById(R.id.toolbar);

        buttonLogout= findViewById(R.id.buttonLogout);

        //if user is not logged in, start the login activity
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        textViewFullName = findViewById(R.id.tvNumber1);
        //textViewCellNumber = findViewById(R.id)
        textViewEmail = findViewById(R.id.tvNumber3);
        //buttonLogout = findViewById(R.id.buttonLogout);

        //getting current user
        User user = SharedPrefManager.getInstance(this).getUser();
        System.out.println("token: " + user.getToken());
        System.out.println("id: " + user.getId());
        System.out.println("full name: " + user.getFullName());

        //setting the values to the textviews
        //textViewUsername.setText(user.getUsername());
        textViewEmail.setText(user.getEmail());
        //textViewId.setText(user.getId());

        //when the user presses logout button
        //calling the logout method
        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();

                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
                finish();

                System.out.println("!!!!!!!!!!!!!!LOGOUT!!!!!!!!!!!!!");
            }
        });
    }
}





