package kz.test.spravochnik.di;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import kz.test.spravochnik.SpravochnikApp;

public abstract class BaseDaggerDialogFragment extends DialogFragment {

    @Inject
    protected Bus eventBus;

    protected abstract void onInject();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        SpravochnikApp.get().component().inject(this);
        onInject();
        this.eventBus.register(this);
    }

    public void onDetach() {
        super.onDetach();
        this.eventBus.unregister(this);
    }

    protected View inflateDialogView(int res, ViewGroup root) {
        View v = LayoutInflater.from(getActivity()).inflate(res, root);
        ButterKnife.bind((Object) this, v);
        return v;
    }

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
