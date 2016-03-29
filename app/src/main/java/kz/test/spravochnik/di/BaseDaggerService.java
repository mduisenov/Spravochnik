package kz.test.spravochnik.di;

import android.app.Service;

import kz.test.spravochnik.SpravochnikApp;


public abstract class BaseDaggerService extends Service {


    public BaseDaggerService() {
        SpravochnikApp.get().component().inject(this);
    }
}
