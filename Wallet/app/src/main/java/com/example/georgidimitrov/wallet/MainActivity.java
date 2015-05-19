package com.example.georgidimitrov.wallet;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/*
Created by: Georgi Dimitrov
Date: May 14, 2015
Created on: Android Studio 1.2
Devices tested on: Samsung Galaxy S4 - Android 5.0.1 ; Android Studio emulator running on Macbook Pro
Min sdk version: 14
Notes: All of the requirements are completed excluding the drag and drop feature.
The 2 bonus requirements are also completed. For devices with soft-key menu buttons
(example samsung galaxy line) the menu button will not appear on the top.

If you have any questions feel free to email me at georgi12dimitrov@gmail.com
*/

public class MainActivity extends FragmentActivity implements ActionBar.TabListener, ActionBar.OnNavigationListener {

    private static Context context;
    AppTABsPagerAdapter mAppTABsPagerAdapter;
    ViewPager mViewPager;
    public static ArrayList<CardItem> listT1= new ArrayList<CardItem>();
    public static ArrayList<CardItem> listT2 = new ArrayList<CardItem>();
    public static ArrayList<CardItem> listT3 = new ArrayList<CardItem>();

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = getApplicationContext();
        final ActionBar actionBar = getActionBar();


        // This allows the user to click the top left corner and open the navigation drawer
        actionBar.setHomeButtonEnabled(true);

        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        String mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        for(int i=0;i<5;i++)
        {
            CardItem c = new CardItem();
            c.add("Tab1 " + i, "tab1 " + i, "tab1 " + i, "tab1 " + i, "tab1 " + i, "tab1 " + i );
            listT1.add(i,c);
            CardItem c2 = new CardItem();
            c2.add("Tab2 " + i, "tab2 " + i, "tab2 " + i, "tab2 " + i, "tab2 " + i, "tab2 " + i );
            listT2.add(i,c2);
            CardItem c3 = new CardItem();
            c3.add("Tab3 " + i, "tab3 " + i, "tab3 " + i, "tab3 " + i, "tab3 " + i, "tab3 " + i );
            listT3.add(i,c3);
            Log.v("created them ", " " );
        }

        // Create the adapter that will return a fragment for each of the three primary TABs
        // of the app.
        mAppTABsPagerAdapter = new AppTABsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between TABs.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppTABsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app TABs, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });
        // For each of the TABs in the app, add a tab to the action bar.
        for (int i = 0; i < mAppTABsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppTABsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    private void addDrawerItems() {
        String[] osArray = { "How do I pay?", "My Bank", "Settings" };
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Do Something!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle("Nav Panel");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(getTitle().toString());
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.v("PRESSED", "PRESSED");
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
            Toast.makeText(MainActivity.this, "Do Something!!", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.action_exit)
        {
            System.exit(1);
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        return false;
    }

    public static class AppTABsPagerAdapter extends FragmentPagerAdapter {
        public AppTABsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
                    Fragment fragment0 = new DummyTABFragment();
                    Bundle args0 = new Bundle();
                    args0.putInt(DummyTABFragment.ARG_TAB_NUMBER, i + 1);
                    fragment0.setArguments(args0);
                    return fragment0;
        }

        @Override
        public int getCount() { return 3; }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position==0)
                return "Payment Cards";
            else if (position==1)
                return "Gift Cards";
            else
                return "Loyalty Cards";
        }
    }

    /**
     * A dummy fragment representing a TAB of the app, but that simply displays dummy text.
     * TAB 2
     */
    public static class DummyTABFragment extends Fragment {

        public static final String ARG_TAB_NUMBER = "tab_number";
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.list_view, container, false);
            Bundle args = getArguments();
            final ListView listView= (ListView) rootView.findViewById(R.id.listView);

            if(args.getInt(ARG_TAB_NUMBER)==1)
            {
                listView.setAdapter(new CustomListAdapter(MainActivity.context, listT1));
            }
            else if (args.getInt(ARG_TAB_NUMBER)==2)
            {
                listView.setAdapter(new CustomListAdapter(MainActivity.context, listT2));
            }
            else
            {
                listView.setAdapter(new CustomListAdapter(MainActivity.context, listT3));
            }

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                               int pos, long id) {
                    Log.v("long clicked", "pos: " + pos);

                    return false;
                }
            });
            return rootView;
        }
    }
}
