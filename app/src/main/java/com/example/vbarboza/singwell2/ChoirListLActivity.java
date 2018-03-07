package com.example.vbarboza.singwell2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by evaramirez on 11/20/17.
 * Still need to connect to backend and obtain list of choirs per specific organization
 * Following mitchtabian instagram tutorials https://www.youtube.com/watch?v=Cdn0jEFW6FM
 */

public class ChoirListLActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{
    private static final String TAG = "ChoirListFragment";

    private ListView choirsListView;
    TextView textViewChoirName, textViewOrgtName, textViewDay, textViewStartTime, textViewEndTime, textViewId;
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    SharedPreferences sharedPreferences;
    User user;
    ArrayList<Card> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_choir_list_l);

        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);

        //If user id is not a number, no user is logged in, redirect to LoginActivity
        if (sharedPreferences.getString("id", "id").toString() == "id") {
            System.out.println("id: " + sharedPreferences.getString("id", "id").toString());
            startActivity(new Intent(this, LoginActivity.class));
        }

        user = SharedPrefManager.getInstance(this).getUser();

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Choirs");
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);


        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        choirsListView = findViewById(R.id.listView);

        final ArrayList<String> listchoirs = new ArrayList<>();

        //Add cards to array list, REMOVE these cards and update array with choirs per specifc organization
//        list.add(new Card("drawable://" + R.drawable.iceland, "Traditional Choir"));
//        list.add(new Card("drawable://" + R.drawable.heaven, "Choir 2"));
//        list.add(new Card("drawable://" + R.drawable.milkyway, "Choir 3"));
//        list.add(new Card("drawable://" + R.drawable.national_park, "Choir 4"));
//        list.add(new Card("drawable://" + R.drawable.switzerland, "Choir 5"));
//
        //CustomListAdapter adapter = new CustomListAdapter(this, R.layout.card_layout_main, list);
//        choirsListView.setAdapter(adapter);


        System.out.println("user id: " + sharedPreferences.getString("id", "id").toString());


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_USERS + sharedPreferences.getString("id", "id").toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        System.out.println("inside Choirs onResponse()****************************");

                        //progressBar.setVisibility(View.GONE)
                        System.out.println("Response: " + response);
                        //requestQueue.stop();

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            System.out.println("JSON obj: " + obj);

                            JSONArray choirlist = obj.getJSONArray("choirs");
                            for (int i = 0; i < choirlist.length(); i++) {
                                System.out.println("choir item: " + choirlist.getString(i));
                                listchoirs.add(choirlist.getString(i));
                            }

                            createChoirLists(listchoirs);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error response!!!!!!!!!!!!!!! ");
                error.printStackTrace();
                Toast toast = Toast.makeText(ChoirListLActivity.this, "No choirs for user", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();

            }

        });
        VolleySingleton.getInstance(ChoirListLActivity.this).addToRequestQueue(stringRequest);

    }


    private void createChoirLists(ArrayList<String> choirs) {

        for (int i = 0; i < choirs.size(); i++) {

            System.out.println(i);
            System.out.println(choirs.get(i));

            StringRequest stringRequesti = new StringRequest(Request.Method.GET, URLs.URL_CHOIRS + choirs.get(i),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            System.out.println("inside Choirs onResponse()****************************");

                            //progressBar.setVisibility(View.GONE)
                            System.out.println("Response: " + response);
                            //requestQueue.stop();

                            try {
                                //converting response to json object
                                JSONObject obj = new JSONObject(response);

                                System.out.println("JSON obj: " + obj);

                                String name = obj.getString("name");
                                System.out.println("name: " + name);

                                list.add(new Card("drawable://" + R.drawable.iceland, name));

                                CustomListAdapter adapter = new CustomListAdapter(ChoirListLActivity.this, R.layout.card_layout_main, list);
                                choirsListView.setAdapter(adapter);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Error response!!!!!!!!!!!!!!! ");
                    error.printStackTrace();
                    Toast toast = Toast.makeText(ChoirListLActivity.this, "Choir doesn't exist", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();

                }

            });
            VolleySingleton.getInstance(ChoirListLActivity.this).addToRequestQueue(stringRequesti);
        }

        System.out.println("done with methond");
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
