package kz.test.spravochnik.ui.main.hospital;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import kz.test.spravochnik.R;
import kz.test.spravochnik.SpravochnikApp;
import kz.test.spravochnik.ui.BaseFragment;
import kz.test.spravochnik.ui.main.MainActivity;
import kz.test.spravochnik.ui.main.ViewPagerAdapter;
import kz.test.spravochnik.ui.main.map.HospitalMapFragment;

public class MainHospitalsFragment extends BaseFragment {

    @Bind({R.id.tabs})
    TabLayout mTabLayout;

    @Bind({R.id.viewpager})
    ViewPager mViewPager;

    public static Fragment newInstance() {
        return new MainHospitalsFragment();
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_hospital,null);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        Toolbar toolbar = getMainActivity().getToolbar();
        if (toolbar != null && VERSION.SDK_INT >= 21) {
            toolbar.setElevation(0.0f);
        }
    }

    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    @Override
    protected void onInject() {
        SpravochnikApp.get().component().inject(this);
    }

    private void initViews() {

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getFragmentManager());
        pagerAdapter.addFragment(HospitalsFragment.newInstance(), getString(R.string.menu_hospital));
        pagerAdapter.addFragment(HospitalMapFragment.newInstance(), getString(R.string.map));
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
//        UiHelper.setTitle(getActivity(), R.string.menu_hospital);
    }
}
