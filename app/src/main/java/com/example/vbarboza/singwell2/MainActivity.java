package com.example.vbarboza.singwell2;

import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    EditText editTextUsername, editTextEmail, editTextPassword;
    ProgressBar progressBar;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        progressBar = findViewById(R.id.progressBar);
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);


        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);



        // display the first navigation drawer view on app launch
        displayView(0);

        //if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
            return;
        }

        //editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        //this doesn't break but not sure how to initialize the buttonRegister, so it pulls up register page
//        Button button = findViewById(R.id.buttonRegister);
//        if (button != null) {
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //if user pressed on button register
//                    //here we will register the user to the server
//                    registerUser();
//                }
//            });
//        }
//
//        findViewById(R.id.textViewLogin).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //if user pressed on login, open login screen
//                finish();
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent);
//            }
//        });
  }

//    private void registerUser() {
//        final String username = editTextUsername.getText().toString().trim();
//        final String email = editTextEmail.getText().toString().trim();
//        final String password = editTextPassword.getText().toString().trim();
//
//        //validations
//        if (TextUtils.isEmpty(username)) {
//            editTextUsername.setError("Please enter username");
//            editTextUsername.requestFocus();
//            return;
//        }
//
//        if (TextUtils.isEmpty(email)) {
//            editTextEmail.setError("Please enter email");
//            editTextEmail.requestFocus();
//            return;
//        }
//
//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            editTextEmail.setError("Enter a valid email");
//            editTextEmail.requestFocus();
//            return;
//        }
//
//        if (TextUtils.isEmpty(password)) {
//            editTextPassword.setError("Enter a password");
//            editTextPassword.requestFocus();
//            return;
//        }

//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        progressBar.setVisibility(View.GONE);
//
//                        try {
//                            //convert response to json object
//                            JSONObject obj = new JSONObject(response);
//
//                            //if no error in response
//                            if (!obj.getBoolean("error")) {
//                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//
//                                //get user from the response
//                                JSONObject userJson = obj.getJSONObject("user");
//
//                                //create new user object
//                                User user = new User(
//                                        userJson.getString("username"),
//                                        userJson.getString("password")
//                                );
//
//                                //store user i shared preferences
//                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
//
//                                //start profile activity
//                                finish();
//                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
//                            } else {
//                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("username", username);
//                params.put("email", email);
//                params.put("password", password);
//                return params;
//            }
//        };
//
//        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_search){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
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

                break;
            case 1:
                startActivity(new Intent(this, ChoirListLActivity.class));

                break;
            case 2:
                if (sharedPreferences.getString("id", "id").toString() == null) {
                    startActivity(new Intent(this, LoginActivity.class));

                    System.out.println("main activity menu: id=null");
                } else {
                    startActivity(new Intent(this, ProfileActivity.class));
                    System.out.println("main activity menu: id !=null");

                }

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