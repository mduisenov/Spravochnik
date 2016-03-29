package kz.test.spravochnik.data.prefs;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import kz.test.spravochnik.SpravochnikApp;
import kz.test.spravochnik.helper.BaseConstants.Prefs;

@Singleton
public final class PreferenceWrapper {
    @Inject
    SharedPreferences prefs;

    @Inject
    public PreferenceWrapper() {
        SpravochnikApp.get().component().inject(this);
    }


    public long getCinemasLastUpdate() {
        return this.prefs.getLong(Prefs.LAST_UPDATE_CINEMAS, 0);
    }

    public void setCinemasLastUpdate(long lastUpdate) {
        this.prefs.edit().putLong(Prefs.LAST_UPDATE_CINEMAS, lastUpdate).apply();
    }

    public long getHospitalsLastUpdate() {
        return this.prefs.getLong(Prefs.LAST_UPDATE_HOSPITAL, 0);
    }

    public void setHospitalsLastUpdate(long lastUpdate) {
        this.prefs.edit().putLong(Prefs.LAST_UPDATE_HOSPITAL, lastUpdate).apply();
    }
}
