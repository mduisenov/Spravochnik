/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kz.test.spravochnik.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import kz.test.spravochnik.R;
import kz.test.spravochnik.SpravochnikApp;
import kz.test.spravochnik.misc.UiEvents;
import kz.test.spravochnik.sidemenu.SideMenuDrawerFragment;
import kz.test.spravochnik.ui.BaseMainActivity;
import kz.test.spravochnik.ui.main.cinema.MainCinemasFragment;
import kz.test.spravochnik.ui.main.hospital.MainHospitalsFragment;

public class MainActivity extends BaseMainActivity {

    @Bind({R.id.drawer_layout})
    DrawerLayout mDrawerLayout;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onInject() {
        SpravochnikApp.get().component().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        changeFragment(MainHospitalsFragment.newInstance());

        setSupportActionBar(mToolbar);
        setupSideMenu();
    }

    private void setupSideMenu() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.menu_content);
        if (f instanceof SideMenuDrawerFragment) {
            ((SideMenuDrawerFragment) f).setupDrawer(mDrawerLayout, mToolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openMenuCategory(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_hospital:
                changeFragment(MainHospitalsFragment.newInstance());
                break;
            case R.id.menu_cinema:
                changeFragment(MainCinemasFragment.newInstance());
                break;
            case R.id.menuSettings:
                break;
//                SettingsActivity.startActivity(this);
            case R.id.menuShare:
                Intent sendIntent = new Intent();
                sendIntent.setAction("android.intent.action.SEND");
                sendIntent.putExtra("android.intent.extra.TEXT", "Install amazing app Spravochnik.\n http://spravochnik.kz/");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.menu_share)));
                break;
            default:
        }
    }
    @Subscribe
    public void onNavigationMenuClickedListener(UiEvents.NavigationMenuClicked event) {
        openMenuCategory(event.getMenuItem());
    }
}
