package com.example.vbarboza.singwell2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;


public class ProfileActivity extends AppCompatActivity {

    TextView textViewName, textViewLastName, textViewCellNumber, textViewEmail, textViewUsername, textViewId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_scrolling);

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

}
