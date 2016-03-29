package kz.test.spravochnik.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.ButterKnife;
import kz.test.spravochnik.di.BaseDaggerFragment;

public abstract class BaseFragment extends BaseDaggerFragment {

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    public void onResume() {
        super.onResume();
        this.mBus.register(this);
    }

    public void onPause() {
        super.onPause();
        this.mBus.unregister(this);
    }

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
