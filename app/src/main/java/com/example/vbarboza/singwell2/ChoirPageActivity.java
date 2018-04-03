package com.example.vbarboza.singwell2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
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

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChoirPageActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener  {

    Toolbar mToolbar;
    SharedPreferences sharedPreferences;
    private FragmentDrawer drawerFragment;
    TextView nameTextView, meetsOnTextView, meetTimesTextView, directorNameTextView, eventDateTextView, eventNameTextView, eventLocationTextView, eventTimeTextView;
    //String choirId = "";
    String events = "events";
    String choir;
    ArrayList<Card> list = new ArrayList<>();
    private ListView choirEventsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choir_page);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Choir");
        setSupportActionBar(mToolbar);
        String choirId = getIntent().getStringExtra("choirID");
        choir = choirId+ "/";
        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);

        //If user id is not a number, no user is logged in, redirect to LoginActivity
        if (sharedPreferences.getString("id", "id").toString() == "id"){
            System.out.println("id: " + sharedPreferences.getString("id", "id").toString());
            startActivity(new Intent(this, LoginActivity.class));
        }

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        //choirListEvents is in activity_choir_page.xml, this is how I want the list to look
        choirEventsListView =  findViewById(R.id.choirEventsList);

        ImageView btnRoster = findViewById(R.id.action3);
        ImageView btnMusicLibrary = findViewById(R.id.action2);

        btnRoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRosterActivity(v);
            }
        });

        btnMusicLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMusicLibraryActivity(v);
            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_CHOIRS + choirId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            String name = obj.getString("name");
                            //String meeting_day = "Rehearsal: " + DayOfWeek.of(obj.getInt("meeting_day")).toString();
                            String startHour = obj.getString("meeting_day_start_hour");
                            String endHour = obj.getString("meeting_day_end_hour");
                            String director = obj.getString("director_name");

                            System.out.println("name: " + name);
                            System.out.println("startHour: " + startHour);
                            System.out.println("endHour: " + endHour);
                            System.out.println("director: " + director);
                            String rehearsalTime = startHour + " - " + endHour;

                            nameTextView = findViewById(R.id.tvNumber11);//choir name
                            directorNameTextView = findViewById(R.id.tvNumber12);//director name
                            meetsOnTextView = findViewById(R.id.tvNumber13);//rehearsal day
                            meetTimesTextView = findViewById(R.id.tvNumber14);//rehearsal time


                            nameTextView.setText(name);
                            directorNameTextView.setText(director);
                            //meetsOnTextView.setText(meeting_day);
                            meetTimesTextView.setText(rehearsalTime);

                            //System.out.println("!!!!!!!!!!!!!!!!!!!!! CALLING GETEVENTS() !!!!!!!!!!!!!!!!!!!!!");
                            getEvents();

                            //extracting choirs array from response
                           // JSONArray choristersArray = obj.getJSONArray("choristers");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error response!!!!!!!!!!!!!!! ");
                error.printStackTrace();
                startActivity(new Intent(ChoirPageActivity.this, LoginActivity.class));
                Toast toast = Toast.makeText(ChoirPageActivity.this, "No profile in file", Toast.LENGTH_SHORT);
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
        VolleySingleton.getInstance(ChoirPageActivity.this).addToRequestQueue(stringRequest);
    }

    public void getEvents(){
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!! INSIDE GETEVENTS() !!!!!!!!!!!!!!!!!!!!!!");

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_CHOIRS + choir + events,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //System.out.println("stringRequest: " + URLs.URL_CHOIRS + choir12 + events);
                        try {
                            //converting response to json object
                            JSONArray events = new JSONArray(response);

                            System.out.println("events: " + events);

                            for (int i = 0; i < events.length(); i++) {
                                System.out.println("Inside  for loop !!!!!!!!!!!!!!!!!");
                                JSONObject eventInfo = events.getJSONObject(i);
                                System.out.println("eventInfo: " + eventInfo);

                                String eventName = eventInfo.getString("name");
                                String eventDate = eventInfo.getString("date");
                                String eventTime = eventInfo.getString("time");
                                String eventLocation = eventInfo.getString("location");

                                System.out.println("eventName: " + eventName);
                                System.out.println("eventDate: " + eventDate);
                                System.out.println("eventTime: " + eventTime);
                                System.out.println("eventLocation: " + eventLocation);

                                System.out.println("Adding to list!!!!!!!!!!!!!!!");
                                list.add(new Card("drawable://" + R.drawable.event_black_24x24, eventName, eventDate, eventLocation, eventTime));
                            }

                            System.out.println("Outside for loop!!!!!!!!!!!!!!!");

                            System.out.println("list.size(): " + list.size());

                            CustomListAdapterChoirEvents adapter = new CustomListAdapterChoirEvents(ChoirPageActivity.this, R.layout.choir_events_list, list);
                            System.out.println("Adapter created!!!!!!!!!!!!!!!");

                            choirEventsListView.setAdapter(adapter);

                            System.out.println("choirEventsListView called!!!!!!!!!!!!!!!");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error response!!!!!!!!!!!!!!! ");
                error.printStackTrace();
                startActivity(new Intent(ChoirPageActivity.this, LoginActivity.class));
                Toast toast = Toast.makeText(ChoirPageActivity.this, "No profile in file", Toast.LENGTH_SHORT);
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
        VolleySingleton.getInstance(ChoirPageActivity.this).addToRequestQueue(stringRequest);
    }


    public void goToRosterActivity (View view){
        Intent intent = new Intent (this, RosterActivity.class);
        startActivity(intent);
    }

    public void goToMusicLibraryActivity (View view){
        Intent intent = new Intent (this, MusicLibraryActivity.class);
        startActivity(intent);
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
