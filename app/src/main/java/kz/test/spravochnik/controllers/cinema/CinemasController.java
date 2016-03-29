package kz.test.spravochnik.controllers.cinema;

import com.path.android.jobqueue.JobManager;
import com.squareup.otto.Bus;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import kz.test.spravochnik.SpravochnikApp;
import kz.test.spravochnik.data.LocalCache;
import kz.test.spravochnik.data.api.ApiException;
import kz.test.spravochnik.data.model.cinema.Cinema;
import kz.test.spravochnik.data.model.cinema.CinemaEvents;
import kz.test.spravochnik.data.prefs.PreferenceWrapper;
import kz.test.spravochnik.helper.BaseConstants;
import kz.test.spravochnik.jobqueue.database.RestoreAllCinemasJob;
import kz.test.spravochnik.jobqueue.database.RestoreAllHospitalsJob;
import kz.test.spravochnik.jobqueue.network.cinema.GetCinemasJob;
import kz.test.spravochnik.jobqueue.network.hospital.GetHospitalsJob;
import timber.log.Timber;

public class CinemasController {

    private final Bus mBus;
    private final JobManager mJobManager;
    @Inject
    PreferenceWrapper mPreferencesWrapper;

    public CinemasController(JobManager jobManager, Bus bus) {
        mJobManager = jobManager;
        mBus = bus;
    }

    protected void onInject() {
        SpravochnikApp.get().component().inject(this);
    }

    public void getCinemas() {
        onInject();
        Timber.d("getCinemas() ");
        long lastUpdateArticles = mPreferencesWrapper.getCinemasLastUpdate();
        boolean needToUpdate = isDataNeedToUpdate(lastUpdateArticles);
        if(needToUpdate){
            mJobManager.addJob(new GetCinemasJob(this));
        }else{
            mJobManager.addJobInBackground(new RestoreAllCinemasJob(this));
        }

    }

    public void handleGetCinemasSuccess(List<Cinema> cinemas) {
        onInject();
        Timber.d("handleGetCinemasSuccess() ");
        mPreferencesWrapper.setCinemasLastUpdate(System.currentTimeMillis());
        Timber.d("cinemas was updated ");
        LocalCache.getInstance().storeCinemas(cinemas);
//        mJobManager.addJobInBackground(new RestoreAllCinemasJob(this));
        mBus.post(new CinemaEvents.GetCinemaSuccess(LocalCache.getInstance().readCinemas()));
    }

    public void handleGetCinemaError(ApiException apiException){
        Timber.d("handleGetCinemaError()");
//        mJobManager.addJobInBackground(new RestoreAllCinemasJob(this));
        mBus.post(new CinemaEvents.GetCinemaFailed(apiException));
    }

    public void restoreCinemas(List<Cinema> cinemas){
        Timber.d("restoreCinemas() ");
        mBus.post(new CinemaEvents.GetCinemaSuccess(cinemas));
    }

    private boolean isDataNeedToUpdate(long lastUpdate) {
        return System.currentTimeMillis() - BaseConstants.Prefs.PERIOD_TO_UPDATE > lastUpdate;
    }
}
