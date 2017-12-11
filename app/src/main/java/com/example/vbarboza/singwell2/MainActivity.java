package com.example.vbarboza.singwell2;

//import android.os.Bundle;
//import android.support.design.widget.TabLayout;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//
///**
// * Created by evaramirez on 11/20/17.
// * Following mitchtabian instagram tutorials https://www.youtube.com/watch?v=Cdn0jEFW6FM
// */
//
//public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
//
//    private static final String TAG = "MainActivity";
//
//    SectionsPagerAdapter mSectionsPageAdapter;
//
//    private ViewPager mViewPager;
//    private Toolbar mToolbar;
//    private FragmentDrawer drawerFragment;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_choir_list_l);
//        // Adding Toolbar to Main screen
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//
//        drawerFragment = (FragmentDrawer)
//                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
//        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
//        drawerFragment.setDrawerListener(this);
//
//        Log.d(TAG, "onCreate: Starting.");
//
//        //mSectionsPageAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//
//        /* ViewPager section added to display card list on the main page of the application
//         * will need to remove once a menu has been added*/
//        // Set up the ViewPager with the sections adapter.
//        //mViewPager = (ViewPager) findViewById(R.id.container);
//        //setupViewPager(mViewPager);
//
//        //TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        //tabLayout.setupWithViewPager(mViewPager);
//    }
//
//
//    /* Added this function to display the card list on the main page of the application
//     * will need to remove once a menu has been added */
//    private void setupViewPager(ViewPager viewPager) {
//        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new ChoirListLFragment(),null);
////        adapter.addFragment(new Tab2Fragment(), "");
////        adapter.addFragment(new Tab3Fragment(), "");
//        viewPager.setAdapter(adapter);
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onDrawerItemSelected(View view, int position) {
//
//    }
//}



import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private  static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);
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
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new ChoirListLFragment();
                title = getString(R.string.title_choirs);
                break;
//            case 1:
//                fragment = new FriendsFragment();
//                title = getString(R.string.title_friends);
//                break;
//            case 2:
//                fragment = new MessagesFragment();
//                title = getString(R.string.title_messages);
//                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
}