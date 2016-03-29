package kz.test.spravochnik.sidemenu;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import kz.test.spravochnik.R;
import kz.test.spravochnik.SpravochnikApp;
import kz.test.spravochnik.ui.BaseFragment;
import kz.test.spravochnik.misc.UiEvents;

public class SideMenuDrawerFragment extends BaseFragment {

    private DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;

    @Bind({R.id.navigationView})
    NavigationView mNavigationView;


    private Toolbar mToolbar;

    class PostRunnable implements Runnable {
        PostRunnable() {
        }

        public void run() {
            SideMenuDrawerFragment.this.mDrawerToggle.syncState();
        }
    }

    class OnNavigationItemSelectedListenerImpl implements OnNavigationItemSelectedListener {
        OnNavigationItemSelectedListenerImpl() {
        }

        public boolean onNavigationItemSelected(MenuItem menuItem) {
            menuItem.setChecked(true);
            SideMenuDrawerFragment.this.mDrawerLayout.closeDrawers();
            SideMenuDrawerFragment.this.mBus.post(new UiEvents.NavigationMenuClicked(menuItem));
            return true;
        }
    }

    public static SideMenuDrawerFragment newInstance() {
        SideMenuDrawerFragment f = new SideMenuDrawerFragment();
        f.setArguments(new Bundle());
        return f;
    }

    public void setupDrawer(DrawerLayout upDrawer, Toolbar toolbar) {
        this.mDrawerLayout = upDrawer;
        this.mToolbar = toolbar;
        setUpNavigationDrawer();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_side_menu, container, false);
    }

    protected void onInject() {
        SpravochnikApp.get().component().inject(this);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void initView() {
        this.mNavigationView.setNavigationItemSelectedListener(new OnNavigationItemSelectedListenerImpl());
    }

    private void setUpNavigationDrawer() {
        this.mDrawerToggle = new ActionBarDrawerToggle(getActivity(), this.mDrawerLayout, this.mToolbar, R.string.drawer_open, R.string.drawer_close);
        this.mDrawerLayout.post(new PostRunnable());
        this.mDrawerLayout.setDrawerListener(this.mDrawerToggle);
    }
}
