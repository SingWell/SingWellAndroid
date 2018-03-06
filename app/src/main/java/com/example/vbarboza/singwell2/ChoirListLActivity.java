package com.example.vbarboza.singwell2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import android.content.SharedPreferences;
import java.util.ArrayList;

/**
 * Created by evaramirez on 11/20/17.
 * Still need to connect to backend and obtain list of choirs per specific organization
 * Following mitchtabian instagram tutorials https://www.youtube.com/watch?v=Cdn0jEFW6FM
 */

public class ChoirListLActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    private ListView choirsListView;
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    SharedPreferences sharedPreferences;


    User user;
    //String id  = user.getId();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_choir_list_l);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Choirs");
        setSupportActionBar(mToolbar);

        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        //sharedPreferences.getString("id", null);
        //System.out.println("sharedPreferences: " + sharedPreferences.getString("id", null).toString());


        //if user is not logged in, start the login activity
//        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
//            finish();
//            startActivity(new Intent(this, LoginActivity.class));
//        }

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        choirsListView =  findViewById(R.id.listView);

        //Array of cards
        ArrayList<Card> list = new ArrayList<>();

        //Add cards to array list, REMOVE these cards and update array with choirs per specifc organization
        list.add(new Card("drawable://" + R.drawable.iceland, "Traditional Choir"));
        list.add(new Card("drawable://" + R.drawable.heaven, "Choir 2"));
        list.add(new Card("drawable://" + R.drawable.milkyway, "Choir 3"));
        list.add(new Card("drawable://" + R.drawable.national_park, "Choir 4"));
        list.add(new Card("drawable://" + R.drawable.switzerland, "Choir 5"));

        CustomListAdapter adapter = new CustomListAdapter(this, R.layout.card_layout_main, list);
        choirsListView.setAdapter(adapter);
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
                startActivity(new Intent(this, RosterActivity.class));

                break;
            default:
                break;
        }


    }

//
//
//    @Nullable
//    public onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
//        View view = inflater.inflate(R.layout.fragment_choir_list_l,container,false);
//        choirsListView =  view.findViewById(R.id.listView);
//
//        //Array of cards
//        ArrayList<Card> list = new ArrayList<>();
//
//        //Add cards to array list, REMOVE these cards and update array with choirs per specifc organization
//        list.add(new Card("drawable://" + R.drawable.iceland, "Traditional Choir"));
//        list.add(new Card("drawable://" + R.drawable.heaven, "Choir 2"));
//        list.add(new Card("drawable://" + R.drawable.milkyway, "Choir 3"));
//        list.add(new Card("drawable://" + R.drawable.national_park, "Choir 4"));
//        list.add(new Card("drawable://" + R.drawable.switzerland, "Choir 5"));
//
//        CustomListAdapter adapter = new CustomListAdapter(this, R.layout.card_layout_main, list);
//        choirsListView.setAdapter(adapter);
//
//        return view;
//    }
}
