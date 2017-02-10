package banyan.com.gememployee;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.sdsmdg.tastytoast.TastyToast;

import java.util.HashMap;

import banyan.com.gememployee.global.SessionManager;

public class MainActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    // Session Manager Class
    SessionManager session;

    String str_name;
    public static String str_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(getApplicationContext());

        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        str_name = user.get(SessionManager.KEY_USER);
        str_id = user.get(SessionManager.KEY_USER_ID);

        /**
         *Setup the DrawerLayout and NavigationView
         */

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the Fragment_Services as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new Fragment_ComplaintRegister()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.nav_item_complaints) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new Fragment_ComplaintRegister()).commit();
                }

                if (menuItem.getItemId() == R.id.nav_item_services) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, new Fragment_Services()).commit();

                }

                if (menuItem.getItemId() == R.id.nav_item_reports) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, new Fragment_Reports()).commit();

                }

                if (menuItem.getItemId() == R.id.nav_item_csform) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, new Fragment_CSForm()).commit();

                }

                if (menuItem.getItemId() == R.id.nav_item_about) {

                    TastyToast.makeText(getApplicationContext(), "About...", TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
                }
                if (menuItem.getItemId() == R.id.nav_item_logout) {

                    try {
                        session.logoutUser();
                    }catch (Exception e) {

                    }

                }
                if (menuItem.getItemId() == R.id.nav_item_exit) {

                    TastyToast.makeText(getApplicationContext(), "Exit...", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }

                return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

    }
}
