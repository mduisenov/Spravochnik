package kz.test.spravochnik.di;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;

import kz.test.spravochnik.SpravochnikApp;

public class BaseDaggerBroadcastReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        SpravochnikApp.get().component().inject(this);
    }
}
