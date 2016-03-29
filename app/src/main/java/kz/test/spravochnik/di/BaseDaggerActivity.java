package kz.test.spravochnik.di;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import kz.test.spravochnik.SpravochnikApp;
import kz.test.spravochnik.ui.AppContainer;

public abstract class BaseDaggerActivity extends AppCompatActivity {

    @Inject
    protected Bus eventBus;

    @Inject
    protected AppContainer mAppContainer;

    protected abstract void onInject();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpravochnikApp.get().component().inject(this);
        onInject();
    }

    protected void setCustomContentView(int layoutResId) {
        ButterKnife.bind(this, getLayoutInflater().inflate(layoutResId, mAppContainer.bind(this)));
    }

    protected void onResume() {
        super.onResume();
        this.eventBus.register(this);
    }

    protected void onPause() {
        super.onPause();
        this.eventBus.unregister(this);
    }
}
