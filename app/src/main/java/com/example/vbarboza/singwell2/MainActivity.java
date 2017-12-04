package com.example.vbarboza.singwell2;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by evaramirez on 11/20/17.
 * Following mitchtabian instagram tutorials https://www.youtube.com/watch?v=Cdn0jEFW6FM
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    SectionsPagerAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_choir_list_l);
        // Adding Toolbar to Main screen
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Log.d(TAG, "onCreate: Starting.");

        mSectionsPageAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        /* ViewPager section added to display card list on the main page of the application
         * will need to remove once a menu has been added*/
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    /* Added this function to display the card list on the main page of the application
     * will need to remove once a menu has been added */
    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ChoirListLFragment(),null);
//        adapter.addFragment(new Tab2Fragment(), "");
//        adapter.addFragment(new Tab3Fragment(), "");
        viewPager.setAdapter(adapter);
    }


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
        return super.onOptionsItemSelected(item);
    }

}