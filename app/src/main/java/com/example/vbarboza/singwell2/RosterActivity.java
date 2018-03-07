package com.example.vbarboza.singwell2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.Gravity;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class RosterActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    TextView textViewFullName, textViewEmail;
    SharedPreferences sharedPreferences;
    Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    JSONObject user = new JSONObject();
    //ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roster);

        //listView = findViewById(R.id.list);

        //sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);

        //If user id is not a number, no user is logged in, redirect to LoginActivity
//        if (sharedPreferences.getString("id", "id").toString() == "id"){
//            System.out.println("id: " + sharedPreferences.getString("id", "id").toString());
//            startActivity(new Intent(this, LoginActivity.class));
//        }


        //REMOVE, for debug purpose only
        System.out.println("******************INSIDE ROSTER ACTIVITY*************");

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Choir Rooster");
        setSupportActionBar(mToolbar);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        String id = "20";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_CHOIRS + id,
                //sharedPreferences.getString("id", "id").toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //REMOVE, for debug purpose only
                        System.out.println("inside Roster onResponse()****************************");

                        //REMOVE, for debug purpose only
                        System.out.println("Response: " + response);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            String name = obj.getString("name");
                            String meeting_day = obj.getString("meeting_day");
                            String startHour = obj.getString("meeting_day_start_hour");
                            String endHour = obj.getString("meeting_day_end_hour");

                            System.out.println("name: " + name);
                            System.out.println("meeting_day: " + meeting_day);
                            System.out.println("startHour: " + startHour);
                            System.out.println("endHour: " + endHour);

                            //extracting choirs array from response
                            JSONArray choristersArray = obj.getJSONArray("choristers");
                            System.out.println("Choristers array: " + choristersArray);

                            String[] rosterArray = new String[choristersArray.length()];
                            for(int i = 0; i < 5; i++) {
                                rosterArray[i] = choristersArray.getString(i);
                                //System.out.println("getUser(): " + getUser(rosterArray[i]));
                            }

                            getUser(choristersArray);

//                            for (int i = 0; i < rosterArray.length; i++)
//                                System.out.println("rosterArray[" + i + "]: " + rosterArray[i]);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error response!!!!!!!!!!!!!!! " );
                error.printStackTrace();
                startActivity(new Intent(RosterActivity.this, LoginActivity.class));
                Toast toast = Toast.makeText(RosterActivity.this, "No profile in file",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();
            }

        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("email", textViewEmail.getText().toString());
//                params.put("full_name", textViewFullName.getText().toString());
                //params.put("firstName", editTextPassword.getText().toString());
                return params;
            }
        };


        VolleySingleton.getInstance(RosterActivity.this).addToRequestQueue(stringRequest);

    }

    String userID;
    public JSONObject getUser(JSONArray id) throws JSONException {
        System.out.println("Inside getUSer(), JSONArray : " +id);
        for(int i = 0; i < id.length(); i++) {
            userID = id.getString(i);
            System.out.println("id.getString(i): " + id.getString(i));
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_USERS + userID,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            //REMOVE, for debug purpose only
                            System.out.println("inside Users onResponse()****************************");

                            //REMOVE, for debug purpose only
                            System.out.println("getUser() Response: " + response);

                            try {
                                //converting response to json object
                                user = new JSONObject(response);

                                System.out.println("User: " + user);

                                String email = user.getString("email");
                                String firstName = user.getString("first_name");
                                String lastName = user.getString("last_name");
                                String id = user.getString("id");

                                System.out.println("email: " + email);
                                System.out.println("firstName: " + firstName);
                                System.out.println("lastName: " + lastName);
                                System.out.println("id: " + id);


                                //extracting choirs array from response
                                //JSONArray choristersArray = user.getJSONArray("choristers");
                                //System.out.println("Choristers array: " + choristersArray);

//                                textViewFullName = findViewById(R.id.textView1);
//                                textViewEmail = findViewById(R.id.textView2);
//
//                                textViewFullName.setText(user.getString("first_name"));
//                                textViewEmail.setText(user.getString("email"));

                                //ArrayAdapter<String>adapter = new ArrayAdapter<String>(this, android.R.id.textView1, android.R.id.textView2,  )

//                            String[] rosterArray = new String[choristersArray.length()];
//                            for(int i = 0; i < choristersArray.length(); i++) {
//                                rosterArray[i] = choristersArray.getString(i);
//                                System.out.println("user.getString(\"email\")" + user.getString("email"));
//                                //textViewFullName.setText(rosterArray[i].);
//                            }
//
//                            for (int i = 0; i < rosterArray.length; i++)
//                                System.out.println("rosterArray[" + i + "]: " + rosterArray[i]);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Error response!!!!!!!!!!!!!!! ");
                    error.printStackTrace();
                    startActivity(new Intent(RosterActivity.this, LoginActivity.class));
                    Toast toast = Toast.makeText(RosterActivity.this, "No profile in file", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                }

            })

            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
//                params.put("email", textViewEmail.getText().toString());
//                params.put("full_name", textViewFullName.getText().toString());
                    //params.put("firstName", editTextPassword.getText().toString());
                    return params;
                }
            };

            VolleySingleton.getInstance(RosterActivity.this).addToRequestQueue(stringRequest);
        }
        return user;
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
