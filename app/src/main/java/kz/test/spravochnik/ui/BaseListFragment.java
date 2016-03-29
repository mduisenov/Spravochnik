package kz.test.spravochnik.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import kz.test.spravochnik.misc.NetworkProgressView;


public abstract class BaseListFragment extends BaseFragment {

    protected NetworkProgressView mNetworkProgressView;

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNetworkProgressView = new NetworkProgressView(getContext());
        ((ViewGroup) view).addView(mNetworkProgressView);
    }
}
