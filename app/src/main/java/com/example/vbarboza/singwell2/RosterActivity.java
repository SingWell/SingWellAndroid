package com.example.vbarboza.singwell2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RosterActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    SharedPreferences sharedPreferences;
    Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    JSONObject user = new JSONObject();
    ArrayList<Card> list = new ArrayList<>();
    private ListView choristersListView;
    ImageView btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roster);

        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);

        //If user id is not a number, no user is logged in, redirect to LoginActivity
        if (sharedPreferences.getString("id", "id").toString() == "id") {
            System.out.println("id: " + sharedPreferences.getString("id", "id").toString());
            startActivity(new Intent(this, LoginActivity.class));
        }

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Choir Roster");
        setSupportActionBar(mToolbar);

        //list1 is in activity_roster.xml (this is how I want the list to look)
        choristersListView = findViewById(R.id.list1);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        btn = findViewById(R.id.cardImage);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_USERS + sharedPreferences.getString("id", "id").toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            JSONArray choirs = obj.getJSONArray("choirs");
                             getChoirs(choirs);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        });

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    String choirId;

    public void getChoirs(JSONArray choir) throws JSONException {
        for (int i = 0; i < choir.length(); i++) {
            choirId = choir.getString(i);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_CHOIRS + choirId,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    //converting response to json object
                                    JSONObject obj = new JSONObject(response);

                                    String name = obj.getString("name");
                                    String meeting_day = obj.getString("meeting_day");
                                    String startHour = obj.getString("meeting_day_start_hour");
                                    String endHour = obj.getString("meeting_day_end_hour");
//                                    String id = obj.getString("id");
//                                    System.out.println("id: " + id);
                                    //extracting choirs array from response
                                    JSONArray choristersArray = obj.getJSONArray("choristers");

                                    getUser(choristersArray);

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
        }

    String userID;
    public JSONObject getUser(JSONArray id) throws JSONException {

        for(int i = 0; i < id.length(); i++) {
            userID = id.getString(i);
            System.out.println("Chorister id: " + userID);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_USERS + userID,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                //converting response to json object
                                user = new JSONObject(response);

                                String firstName = user.getString("first_name");
                                String lastName = user.getString("last_name");

                                String fullName = firstName + " " + lastName;

                                //create list of choristers cards to display on listView
                                list.add(new Card("drawable://" + R.drawable.ic_account_circle_black_18dp, fullName, user.getString("email")));

                                //pass data to listView
                                CustomListAdapter2 adapter = new CustomListAdapter2(RosterActivity.this, R.layout.rowlayout, list);
                                choristersListView.setAdapter(adapter);


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
                startActivity(new Intent(this, MusicLibraryActivity.class));

                break;
            default:
                break;
        }
    }
}
