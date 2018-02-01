package com.example.vbarboza.singwell2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class LoginActivity extends AppCompatActivity {
    public LoginActivity() {
        // Required empty public constructor
    }

    EditText editTextUsername, editTextEmail, editTextPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
            return;
        }

        //editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        //editTextPassword = findViewById(R.id.editTextPassword);

        findViewById(R.id.textViewLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on login, open login screen
                finish();
                startActivity(new Intent( LoginActivity.this, LoginActivity.class));
            }
        });
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.activity_login, container, false);
//
//
//        // Inflate the layout for this fragment
//        return rootView;
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//    }
}
