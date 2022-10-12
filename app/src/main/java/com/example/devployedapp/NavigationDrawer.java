
package com.example.devployedapp;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.devployedapp.databinding.ActivityNavigationDrawerBinding;

public class NavigationDrawer extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavigationDrawerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* binding = ActivityNavigationDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarNavigationDrawer.toolbar);

        DrawerLayout drawer = binding.drawerLayout; //this is referring to activity_navigation_drawer.xml
        NavigationView navigationView = binding.navView; //this is referring to activity_navigation_drawer.xml (navView is under DrawerLayout)
*/

/* Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
        * A top-level destination page will have a 'hamburger icon' (3 horizontal lines) on the toolbar to access the drawer.
        * All other pages will have a back arrow button that automatically returns to the home page.
        * These menu ids are designed/created in activity_main_drawer.xml */

        /*mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.profilePage)
                .setOpenableLayout(drawer)
                .build();*/


/* NavController controls what happens when clicking items in the drawer
        * .setupActionBarWithNavController integrates the navigation view with the toolbar (creates the hamburger and back arrow icons). Remove to make the navigation drawer accessible by swiping only.
        * .setupWithNavController connects the drawer view with the NavController (making items in navigationView clickable) */

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation_drawer);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

/* Inflate the menu; this adds items to the action bar if it is present.
        * This menu refers to the 3 vertical dots on the left side of the toolbar. When 3 vertical dots are clicked, it opens a drop-down menu (like Settings).
        * These items are designed/created in navigation_drawer.xml */

        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        //implements the back arrow button
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation_drawer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
