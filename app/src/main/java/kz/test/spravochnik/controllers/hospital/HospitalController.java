package kz.test.spravochnik.controllers.hospital;

import com.path.android.jobqueue.JobManager;
import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

import kz.test.spravochnik.SpravochnikApp;
import kz.test.spravochnik.data.LocalCache;
import kz.test.spravochnik.data.api.ApiException;
import kz.test.spravochnik.data.model.hospital.Hospital;
import kz.test.spravochnik.data.model.hospital.HospitalEvents;
import kz.test.spravochnik.helper.BaseConstants;
import kz.test.spravochnik.data.prefs.PreferenceWrapper;
import kz.test.spravochnik.jobqueue.database.RestoreAllHospitalsJob;
import kz.test.spravochnik.jobqueue.network.hospital.GetHospitalsJob;
import timber.log.Timber;

public class HospitalController {

    private final Bus mBus;
    private final JobManager mJobManager;
    @Inject
    PreferenceWrapper mPreferencesWrapper;

    public HospitalController(JobManager jobManager, Bus bus) {
        mJobManager = jobManager;
        mBus = bus;
    }

    protected void onInject() {
        SpravochnikApp.get().component().inject(this);
    }

    public void fetchHospitals() {
        mJobManager.addJob(new GetHospitalsJob(this));
    }

    public void getHospitals() {
        Timber.d("getHospitals");
        onInject();
        long lastUpdateArticles = mPreferencesWrapper.getHospitalsLastUpdate();
        boolean needToUpdate = isDataNeedToUpdate(lastUpdateArticles);
        if(needToUpdate){
            mJobManager.addJob(new GetHospitalsJob(this));
        }else{
            mJobManager.addJobInBackground(new RestoreAllHospitalsJob(this));
        }

    }

    public void handleGetHospitalsSuccess(List<Hospital> hospitals) {
        Timber.d("handleGetHospitalsSuccess");
        onInject();
        mPreferencesWrapper.setHospitalsLastUpdate(System.currentTimeMillis());
        Timber.d("hospitals was updated at ");
        LocalCache.getInstance().storeHospitals(hospitals);
        mJobManager.addJobInBackground(new RestoreAllHospitalsJob(this));
//        mBus.post(new HospitalEvents.GetHospitalSuccess(LocalCache.getInstance().readHospitals()));
    }

    public void handleGetHospitalError(ApiException apiException) {
        Timber.d("handleGetHospitalError");
        mJobManager.addJobInBackground(new RestoreAllHospitalsJob(this));
        mBus.post(new HospitalEvents.GetHospitalFailed(apiException));
    }

    public void restoreHospitals(List<Hospital> hospitals){
        Timber.d("restoreHospitals");
        LocalCache.getInstance().storeHospitals(hospitals);
        mBus.post(new HospitalEvents.GetHospitalSuccess(LocalCache.getInstance().readHospitals()));
    }

    private boolean isDataNeedToUpdate(long lastUpdate) {
        return System.currentTimeMillis() - BaseConstants.Prefs.PERIOD_TO_UPDATE > lastUpdate;
    }
}
