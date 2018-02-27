package com.example.vbarboza.singwell2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
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

public class ProfileActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    TextView textViewName, textViewLastName, textViewCellNumber, textViewEmail, textViewFullName, textViewId;
    Button buttonLogout;

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        System.out.println("******************INSIDE PROFILE ACTIVITY*************");
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Profile");
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);


        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);


        buttonLogout= findViewById(R.id.buttonLogout);

        //if user is not logged in, start the login activity
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


        buttonLogout = findViewById(R.id.buttonLogout);

        //getting current user
        User user = SharedPrefManager.getInstance(this).getUser();
        //System.out.println("Full name: " + user.getFullName());
        System.out.println("id: " + user.getId());
        System.out.println("full name: " + user.getFullName());



        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_USERS + user.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("inside Profile onResponse()****************************");

                        //progressBar.setVisibility(View.GONE)
                        System.out.println("Response: " + response);
                        //requestQueue.stop();

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
    //
    //                                            if (!obj.getBoolean("error")) {
    //                                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            System.out.println("JSON obj: " + obj);
                            String email = obj.getString("email");
                            System.out.println("email: " + email);
                            String firstName = obj.getString("first_name");
                            System.out.println("First name: " + firstName);
                            String lastName = obj.getString("last_name");
                            System.out.println("Last name: " + lastName);
//                            String token = obj.getString("token");
//                            System.out.println("token: " + token);
                            String id = obj.getString("id");
                            System.out.println("Id: " + id);
                            //String cell = obj.getString("cell")
                            //textViewEmail = findViewById(R.id.editTextEmail);

                            //creating a new user object
                            User user = new User(email, id, firstName, lastName);

                            System.out.println("User id: " + user.getId());
                            System.out.println("User email: " + user.getEmail());
                            //System.out.println("User token: " + user.getToken());
                            System.out.println("Full name " + user.getFullName());


                            textViewFullName = findViewById(R.id.tvNumber1);
                            //textViewCellNumber = findViewById(R.id.)
                            textViewEmail = findViewById(R.id.tvNumber3);
                            //setting the values to the textviews
                            textViewFullName.setText(user.getFullName());
                            textViewEmail.setText(user.getEmail());
                            //textViewId.setText(user.getId());

                            //storing the user in shared preferences
                            //SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                            //starting the profile activity
//                            finish();
//                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

//                                            } else {
//                                                Toast.makeText(LoginActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
//                                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            System.out.println("Error response!!!!!!!!!!!!!!! " );
            error.printStackTrace();
            Toast toast = Toast.makeText(ProfileActivity.this, "No profile in file",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
//            editTextEmail.setText("");
//            editTextPassword.setText("");

        }

        })

        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", textViewEmail.getText().toString());
                params.put("full_name", textViewFullName.getText().toString());
                //params.put("firstName", editTextPassword.getText().toString());
                return params;
            }
        };


        VolleySingleton.getInstance(ProfileActivity.this).addToRequestQueue(stringRequest);
    //}

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
                editor.apply();

                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
                finish();

                System.out.println("!!!!!!!!!!!!!!LOGOUT!!!!!!!!!!!!!");
            }
        });
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
                //Intent startLoginActivity = new Intent(this, LoginActivity.class);
                startActivity(new Intent(this, LoginActivity.class));
                //startActivity(new Intent(this, LoginActivity.class));

                break;
            case 4:
                startActivity(new Intent(this, RegisterActivity.class));

                break;
            default:
                break;
        }


    }

}





