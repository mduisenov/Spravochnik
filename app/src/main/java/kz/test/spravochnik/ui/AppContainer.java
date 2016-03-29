package kz.test.spravochnik.ui;

import android.app.Activity;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import kz.test.spravochnik.R;

public interface AppContainer {

    ViewGroup bind(Activity activity);

    AppContainer DEFAULT = new AppContainer() {
        @Override
        public ViewGroup bind(Activity activity) {
            return ButterKnife.findById(activity, R.id.container);
        }
    };
}
