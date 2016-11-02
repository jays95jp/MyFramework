package com.kevadiyak.myframework.navigators;

import android.Manifest;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.kevadiyak.mvvmarchitecture.common.Navigator;
import com.kevadiyak.myframework.R;
import com.kevadiyak.myframework.fragments.AdapterFragment;
import com.kevadiyak.myframework.fragments.DialogFragment;
import com.kevadiyak.myframework.fragments.FileFragment;
import com.kevadiyak.myframework.fragments.FontFragment;
import com.kevadiyak.myframework.fragments.HomeFragment;
import com.kevadiyak.myframework.fragments.LocationFragment;
import com.kevadiyak.myframework.fragments.OtherFragment;
import com.kevadiyak.myframework.fragments.PermissionFragment;
import com.kevadiyak.myframework.fragments.PhotoFragment;
import com.kevadiyak.myframework.fragments.PreferenceFragment;
import com.kevadiyak.myframework.fragments.ValidationFragment;
import com.kevadiyak.rxpermissions.PermissionResult;
import com.kevadiyak.rxpermissions.RxPermissions;

public class MainNavigator implements Navigator, OnNavigationItemSelectedListener {

    private final FragmentActivity activity;
    private final DrawerLayout drawer;
    private Fragment fragment = null;

    /**
     * Create MainNavigator
     *
     * @param activity the activity that contains fragments container
     * @param drawer   the drawer contains {@link android.support.design.widget.NavigationView}
     */
    public MainNavigator(FragmentActivity activity, DrawerLayout drawer) {
        this.activity = activity;
        this.drawer = drawer;
    }

    public void setHomeFragment() {
        Fragment fragment = null;
        fragment = new HomeFragment();

        activity.getSupportFragmentManager().beginTransaction()
                .add(R.id.content, fragment).commit();
        activity.setTitle("Home");
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_permission:
                fragment = new PermissionFragment();
                break;

            case R.id.nav_photo:
                fragment = new PhotoFragment();
                break;

            case R.id.nav_dialog:
                fragment = new DialogFragment();
                break;

            case R.id.nav_custom_font:
                fragment = new FontFragment();
                break;

            case R.id.nav_location:
                fragment = new LocationFragment();
                break;

            case R.id.nav_preference:
                fragment = new PreferenceFragment();
                break;

            case R.id.nav_validation:
                fragment = new ValidationFragment();
                break;

            case R.id.nav_file:
                fragment = new FileFragment();
                break;

            case R.id.nav_other:
                fragment = new OtherFragment();
                break;

            case R.id.nav_adapter:
                fragment = new AdapterFragment();
                break;
        }
        if (fragment == null)
            return false;

        chnageFragment(item);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void chnageFragment(MenuItem item) {
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment).commit();
        item.setChecked(true);
        activity.setTitle(item.getTitle());
    }
}
