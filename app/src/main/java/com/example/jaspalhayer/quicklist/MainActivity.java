package com.example.jaspalhayer.quicklist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LoginFragment.OnLoginCallback {
    MenuItem register;
    MenuItem login;
    MenuItem logout;
    NavigationView navigationView;
    UserCredentialHandler userStatus;

    protected static final String KEY_USER_STATUS = "USER_STATUS";
    protected static final String USER_PREFS = "userNamePrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userStatus = new UserCredentialHandler();

        final HomeFragment homeFragment = new HomeFragment();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CreateListingActivity.class);
                startActivity(i);
            }
        });

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        register = navigationView.getMenu().getItem(0);
        login = navigationView.getMenu().getItem(1);
        logout = navigationView.getMenu().getItem(2);

        if(userStatus.checkIfUserIsLoggedIn(getApplicationContext())){
            userStatus.setNavHeaderOnLogin(getApplicationContext(), navigationView);
            updateNavDrawer("login", register, login, logout);
        } else {
            userStatus.setNavHeaderOnLogout(getApplicationContext(), navigationView);
            updateNavDrawer("logout", register, login, logout);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.main_container,homeFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onLoginSuccess() {
        userStatus.setNavHeaderOnLogin(getApplicationContext(), navigationView);

        updateNavDrawer("login",register,login,logout);
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_main, menu);
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
        } else if (id == R.id.search_mag_icon){
            SearchFragment searchFragment = new SearchFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, searchFragment)
                    .addToBackStack(null)
                    .commit();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_register) {
            final RegisterFragment registerFragment = new RegisterFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, registerFragment)
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_login) {
            final LoginFragment loginFragment = new LoginFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, loginFragment)
                    .addToBackStack(null)
                    .commit();

        } else if(id == R.id.nav_logout) {
            userStatus.logoutUser(getApplicationContext());
            userStatus.setNavHeaderOnLogout(getApplicationContext(), navigationView);
            updateNavDrawer("logout",register,login,logout);

        } else if (id == R.id.nav_my_listings) {
            // navigate to my listings
        } else if (id == R.id.nav_how) {
            // navigate to how it works page
        } else if (id == R.id.nav_help) {
            // navigate to help page
        } else if (id == R.id.nav_contact_us) {
            // navigate to contact page,
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateNavDrawer(String action, MenuItem register, MenuItem login, MenuItem logout){
        if(action=="login"){
            register.setVisible(false);
            login.setVisible(false);
            logout.setVisible(true);
        } else {
            register.setVisible(true);
            login.setVisible(true);
            logout.setVisible(false);
        }
    }

}
