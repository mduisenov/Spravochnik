package kz.test.spravochnik.bus;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;


import android.os.Looper;
import android.os.Handler;

import com.squareup.otto.Bus;

public class MainThreadEventBus extends Bus {
    private final Handler mainThread;

    public MainThreadEventBus() {
        this.mainThread = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(final Object o) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(o);
            return;
        }
        this.mainThread.post((Runnable) new Runnable() {
            @Override
            public void run() {
                MainThreadEventBus.this.post(o);
            }
        });
    }
}
