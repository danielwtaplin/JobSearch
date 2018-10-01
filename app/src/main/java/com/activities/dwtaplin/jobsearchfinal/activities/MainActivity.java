package com.activities.dwtaplin.jobsearchfinal.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.activities.dwtaplin.jobsearchfinal.R;
import com.activities.dwtaplin.jobsearchfinal.actors.User;
import com.activities.dwtaplin.jobsearchfinal.components.Job;
import com.activities.dwtaplin.jobsearchfinal.database.LocalDatabaseManager;
import com.activities.dwtaplin.jobsearchfinal.database.ServerManager;
import com.activities.dwtaplin.jobsearchfinal.fragments.FilterFragment;
import com.activities.dwtaplin.jobsearchfinal.fragments.ListFragment;
import com.activities.dwtaplin.jobsearchfinal.fragments.MainFragment;
import com.activities.dwtaplin.jobsearchfinal.fragments.NewListingFragment;
import com.activities.dwtaplin.jobsearchfinal.fragments.ProfileFragment;
import com.activities.dwtaplin.jobsearchfinal.fragments.SwipeFragment;
import com.activities.dwtaplin.jobsearchfinal.fragments.UploadFragment;
import com.activities.dwtaplin.jobsearchfinal.fragments.WatchlistFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainFragment.OnFragmentInteractionListener, SwipeFragment.OnFragmentInteractionListener,
        ListFragment.OnFragmentInteractionListener, FilterFragment.OnFragmentInteractionListener, WatchlistFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener, NewListingFragment.OnFragmentInteractionListener, UploadFragment.OnFragmentInteractionListener {
    private TabLayout.Tab jobTab, peopleTab;
    private Toolbar toolbar;
    private ArrayList<Job> jobArrayList = null;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalDatabaseManager localDatabaseManager = new LocalDatabaseManager(this);
        if(!localDatabaseManager.userExists()) {
            startActivity(new Intent(this, LogInActivity.class));
        }
        else{
            user = localDatabaseManager.getUser();
        }
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Job Search");

        //AssetManager assetManager = this.getApplicationContext().getAssets();
       // Typeface typeface = Typeface.createFromAsset(assetManager, String.format(Locale.US, "fonts/%s", "PictorialSignature.ttf"));
       // txtTitle.setTypeface(typeface);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Fragment fragment = new MainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
        fab.setVisibility(View.GONE);
        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            new LocalDatabaseManager(this).clearUser();
            AsyncTask.execute(()->new ServerManager(this).updateToken(user.getServerId(), ""));
            startActivity(new Intent(this,LogInActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_home) {
            fragment = new MainFragment();
        } else if (id == R.id.nav_watchlist) {
            fragment = new WatchlistFragment();
        } else if (id == R.id.nav_profile) {
            fragment = new ProfileFragment();
        } else if (id == R.id.nav_manage) {
            fragment = new UploadFragment();

        } else if (id == R.id.nav_browser) {
             Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.1.76:4200/"));
             startActivity(browserIntent);

        } else if (id == R.id.nav_message) {

        }
        else if (id == R.id.nav_add) {
            fragment = new NewListingFragment();
        }

        if(fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frameLayout, fragment).commit();
            ft.addToBackStack(null);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Toolbar getToolbar() { return toolbar;}

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public ArrayList<Job> getJobArrayList() {
        return jobArrayList;
    }

    public void setJobArrayList(ArrayList<Job> jobArrayList) {
        this.jobArrayList = jobArrayList;
    }

    public User getUser(){
        return user;
    }
}
