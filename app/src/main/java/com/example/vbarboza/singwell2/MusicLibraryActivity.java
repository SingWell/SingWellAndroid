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
import android.widget.ImageView;
import android.widget.ListView;
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


public class MusicLibraryActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    SharedPreferences sharedPreferences;
    Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    ArrayList<Card> list = new ArrayList<>();
    private ListView choristersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_library);

        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);

        //If user id is not a number, no user is logged in, redirect to LoginActivity
        if (sharedPreferences.getString("id", "id").toString() == "id") {
            System.out.println("id: " + sharedPreferences.getString("id", "id").toString());
            startActivity(new Intent(this, LoginActivity.class));
        }

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Music Library");
        setSupportActionBar(mToolbar);

        //list1 is in activity_roster.xml (this is how I want the list to look)
        choristersListView = findViewById(R.id.music_library_list);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_MUSICLIBRAY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONArray library = new JSONArray(response);

                            for (int i=0;i<library.length();i++) {
                                JSONObject music = new JSONObject(library.getString(i));

                                String title = music.getString("title");
                                String composer = music.getString("composer");
                                String instrumentation = music.getString("instrumentation");

                                System.out.println("title: " + title);
                                System.out.println("composer: " + composer);

                                if(instrumentation == "null") {
                                    instrumentation = " ";
                                    System.out.println("instrumentation: " + instrumentation);
                                }

                                if(composer == "null") {
                                    composer = " ";
                                    System.out.println("composer: " + composer);
                                }

                                list.add(new Card("drawable://" + R.drawable.ic_music_note_black_18dp, title, composer,instrumentation));

                                CustomListAdapterMusicLibrary adapter = new CustomListAdapterMusicLibrary(MusicLibraryActivity.this, R.layout.music_library_row_layout, list);
                                choristersListView.setAdapter(adapter);
                            }

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
