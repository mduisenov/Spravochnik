package kz.test.spravochnik.di;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import kz.test.spravochnik.SpravochnikApp;

public abstract class BaseDaggerFragment extends Fragment {
    @Inject
    protected Bus mBus;

    protected abstract void onInject();

    public void onAttach(Activity activity) {
        SpravochnikApp.get().component().inject(this);
        onInject();
        super.onAttach(activity);
    }
}
