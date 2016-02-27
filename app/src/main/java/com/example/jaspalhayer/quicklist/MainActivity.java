package com.example.jaspalhayer.quicklist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.facebook.FacebookSdk;
import com.sendbird.android.SendBird;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LoginFragment.OnLoginCallback {
    MenuItem register;
    MenuItem login;
    MenuItem logout;
    MenuItem completeListing;
    MenuItem expiredListing;
    MenuItem activeListing;
    Bundle bundle;
    SharedPreferences prefs;
    String APP_ID = "FDBEF958-BCF1-4A23-A20F-C4625D2E9C7A";

    NavigationView navigationView;
    UserCredentialHandler userStatus;
    ConnectionHandler request;

    protected static final String KEY_USER_STATUS = "USER_STATUS";
    protected static final String USER_PREFS = "userNamePrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userStatus = new UserCredentialHandler();
        request = new ConnectionHandler();
        bundle = new Bundle();
        FacebookSdk.sdkInitialize(getApplicationContext());
        SendBird.init(APP_ID);

        prefs = getSharedPreferences(USER_PREFS, 0);

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
                if(userStatus.checkIfUserIsLoggedIn(getApplicationContext())) {
                    Intent i = new Intent(getApplicationContext(), CreateListingActivity.class);
                    i.putExtra("COME_FROM", "main");
                    startActivity(i);
                } else {
                    LoginFragment loginFragment = new LoginFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();

                    FragmentTransaction fragmentTransaction = fragmentManager
                            .beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, loginFragment).addToBackStack(null);
                    fragmentTransaction.commit();

                    Snackbar snackbar = Snackbar.make(view, "You are not logged in, please login to create a listing", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getMenuItems(navigationView);

        if (userStatus.checkIfUserIsLoggedIn(getApplicationContext())) {
            userStatus.setNavHeaderOnLogin(getApplicationContext(), navigationView);
            updateNavDrawer("login", register, login, logout, expiredListing, completeListing, activeListing);
        } else {
            userStatus.setNavHeaderOnLogout(navigationView);
            updateNavDrawer("logout", register, login, logout, expiredListing, completeListing, activeListing);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.main_container, homeFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onLoginSuccess() {
        HomeFragment homeFragment = new HomeFragment();
        userStatus.setNavHeaderOnLogin(getApplicationContext(), navigationView);

        updateNavDrawer("login", register, login, logout, expiredListing, completeListing, activeListing);

        register.setVisible(false);
        login.setVisible(false);
        logout.setVisible(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.main_container, homeFragment);
        fragmentTransaction.commit();
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
        } else if (id == R.id.search_mag_icon) {
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

        } else if (id == R.id.nav_logout) {
            userStatus.logoutUser(getApplicationContext());
            userStatus.setNavHeaderOnLogout(navigationView);
            updateNavDrawer("logout", register, login, logout, expiredListing, completeListing, activeListing);

        } else if (id == R.id.nav_how) {
            // navigate to how it works page
        } else if (id == R.id.nav_help) {
            // navigate to help page
        } else if (id == R.id.nav_contact_us) {
            // navigate to contact page,
        } else if (id == R.id.nav_complete_list) {
            String userId = prefs.getString("NAV_EMAIL", null);

            request.getUserListings(this, "2", userId, new ConnectionHandler.VolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) {
                    Intent i = new Intent(getApplicationContext(), ResultActivity.class);
                    i.putExtra("jsonObject", result.toString());
                    i.putExtra("CAME_FROM", "complete");
                    startActivity(i);
                }
            });
        } else if (id == R.id.nav_expired_list) {
            String userId = prefs.getString("NAV_EMAIL", null);

            request.getUserListings(this, "0", userId, new ConnectionHandler.VolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) {
                    Intent i = new Intent(getApplicationContext(), ResultActivity.class);
                    i.putExtra("CAME_FROM", "expired");
                    i.putExtra("jsonObject", result.toString());
                    startActivity(i);
                }
            });

        } else if (id == R.id.nav_my_active_listings) {
            String userId = prefs.getString("NAV_EMAIL", null);

            request.getUserListings(this, "1", userId, new ConnectionHandler.VolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) {
                    Intent i = new Intent(getApplicationContext(), ResultActivity.class);
                    i.putExtra("CAME_FROM", "active");
                    i.putExtra("jsonObject", result.toString());
                    startActivity(i);
                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateNavDrawer(String action, MenuItem register, MenuItem login, MenuItem logout, MenuItem expiredListing, MenuItem completeListing, MenuItem activeListing) {
        if (action == "login") {
            register.setVisible(false);
            login.setVisible(false);
            logout.setVisible(true);
            completeListing.setVisible(true);
            activeListing.setVisible(true);
            expiredListing.setVisible(true);
        } else {
            register.setVisible(true);
            login.setVisible(true);
            logout.setVisible(false);
            completeListing.setVisible(false);
            activeListing.setVisible(false);
            expiredListing.setVisible(false);
        }
    }

    private void getMenuItems(NavigationView nv) {
        register = nv.getMenu().getItem(0);
        login = nv.getMenu().getItem(1);
        logout = nv.getMenu().getItem(2);
        activeListing = nv.getMenu().getItem(3);
        completeListing = nv.getMenu().getItem(4);
        expiredListing = nv.getMenu().getItem(5);
    }
}
