package com.example.vbarboza.singwell2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by evaramirez on 1/25/18.
 */

public class RegisterActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    EditText editTextPassword, editTextEmail;
    ProgressBar progressBar;
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);


        //if the user is already logged in we will directly start the profile activity
        //This will be used to get instance of user already logged in
//        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
//            finish();
//            startActivity(new Intent(this, ProfileActivity.class));
//        }

        TextView tv1;
        tv1 = findViewById(R.id.textViewLogin);

        tv1.setOnClickListener (new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        progressBar = findViewById(R.id.progressBar);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextEmail);



        //if user presses on login
        //calling the method login
//        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {

        //Not sure how to call login activity, this breaks Register
//                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//            }
//        });
    }

    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        //first we will do the validations

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Enter a password");
            editTextPassword.requestFocus();
            return;
        }

        //Still need to add code to connect to the database
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("user");

//                                //creating a new user object
//                                User user = new User(
//                                        userJson.getString("id"),
//                                        userJson.getString("password"),
//                                        userJson.getString("email")
//                                        //userJson.getString("token")
//                                );

                                //storing the user in shared preferences
                                //SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
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

