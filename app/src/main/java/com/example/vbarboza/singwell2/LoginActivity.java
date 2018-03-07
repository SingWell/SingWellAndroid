package com.example.vbarboza.singwell2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class LoginActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    public final static String MyPREFERENCES = "MyPrefs";
    private EditText editTextCellPhone;
    private EditText editTextEmail;
    private EditText editTextPassword;
    Button buttonLogin;
    TextView textView;
    SharedPrefManager sharedPrefManager;

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Login");
        setSupportActionBar(mToolbar);

        getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);


        LoginButton();

        //if the user is already logged in we will directly start the profile activity
        //This will be used to get instance of user already logged in
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
            System.out.println("LOGIN ACTIVITY: USER LOGGED IN");
            return;
        }

        //If the user does not have an account, redirect to Register page
        TextView tvRegister;
        tvRegister = findViewById(R.id.textViewRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    //Once login button is pressed, get username and password, validate, and allow access to account
    //or block user after 5 attempts
    public void LoginButton() {
        System.out.println("inside LoginButton()******************************");

        //get the values
        //editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textView = findViewById(R.id.textViewLogin);



        buttonLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                        final Editor editor = preferences.edit();

                        System.out.println("inside onClick()****************************");
                        System.out.println("inside URL_LOGIN: " + URLs.URL_LOGIN + " ****************************");


                        //if username is empty
                         if (TextUtils.isEmpty(editTextEmail.getText().toString())) {
                            editTextEmail.setError("Please enter your email address");
                            editTextEmail.requestFocus();
                            return;
                        }

                        //if password is empty
                        if (TextUtils.isEmpty(editTextPassword.getText().toString())) {
                            editTextPassword.setError("Please enter your password");
                            editTextPassword.requestFocus();
                            return;
                        }

                        //final RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

                        //get string from server
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                                new Response.Listener<String>() {

                                    @Override
                                    public void onResponse(String response) {
                                        System.out.println("inside onResponse()****************************");

                                        //progressBar.setVisibility(View.GONE);

                                        System.out.println("Response: " + response);
                                        //requestQueue.stop();

                                        try {
                                            //converting response to json object
                                            JSONObject obj = new JSONObject(response);
//
//                                            if (!obj.getBoolean("error")) {
//                                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                                System.out.println("JSON obj: " + obj);
                                                String token = obj.getString("token");
                                                editor.putString("token", token);
                                                System.out.println("Token: " + token);
                                                String id = obj.getString("user_id");
                                                System.out.println("Id: " + id);
                                                editor.putString("id", id);

                                                if (obj.has("profile")){
                                                    System.out.println("HAS PROFILE");
                                                }else{
                                                    System.out.println("NO PROFILE in json obj");
                                                }

//                                                JSONArray ownedOrganizations = obj.getJSONArray("owned_organizations");
//                                                JSONObject profile = obj.getJSONObject("profile");
//                                                System.out.println("obj.profile: " + ownedOrganizations);
//

//                                              String firstName = obj.getString("firstName");
//                                              System.out.println("First name: " + firstName);

//                                              String la = obj.getString("user_id");
//                                              System.out.println("Id: " + id);
                                                //editTextEmail = findViewById(R.id.editTextEmail);

                                                //creating a new user object
                                                User user = new User(editTextEmail.getText().toString(), id, token);

                                                System.out.println("User id: " + user.getId());
                                                System.out.println("User email: " + user.getEmail());
                                                editor.putString("email", user.getEmail());
                                                editor.commit();
                                                System.out.println("User token: " + user.getToken());

                                                //storing the user in shared preferences
                                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);



                                                //starting the profile activity
                                                //finish();
                                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

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
                                        Toast toast = Toast.makeText(LoginActivity.this, "Incorrect email or password",Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.TOP, 0, 0);
                                        toast.show();
                                        editTextEmail.setText("");
                                        editTextPassword.setText("");

                                    }
                                })

                        {

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError{
                                Map<String, String> params = new HashMap<>();
                                params.put("email", editTextEmail.getText().toString());
                                params.put("password", editTextPassword.getText().toString());
                                return params;
                            }
                        };


                        VolleySingleton.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);
                    }

                }
        );
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


