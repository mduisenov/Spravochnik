package kz.test.spravochnik.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList;
    private final List<String> mFragmentTitleList;

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
        this.mFragmentList = new ArrayList();
        this.mFragmentTitleList = new ArrayList();
    }

    public Fragment getItem(int position) {
        return this.mFragmentList.get(position);
    }

    public int getCount() {
        return this.mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        this.mFragmentList.add(fragment);
        this.mFragmentTitleList.add(title);
    }

    public CharSequence getPageTitle(int position) {
        return this.mFragmentTitleList.get(position);
    }
}
