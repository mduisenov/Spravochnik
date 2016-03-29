package kz.test.spravochnik.controllers;

import com.path.android.jobqueue.JobManager;
import com.squareup.otto.Bus;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kz.test.spravochnik.controllers.cinema.CinemasController;
import kz.test.spravochnik.controllers.hospital.HospitalController;
import kz.test.spravochnik.data.prefs.PreferenceWrapper;

@Module
public class ControllerModule {

    @Singleton
    @Provides
    HospitalController provideHospitalController(JobManager jobManager, Bus bus) {
        return new HospitalController(jobManager, bus);
    }

    @Singleton
    @Provides
    CinemasController provideCinemaController(JobManager jobManager, Bus bus) {
        return new CinemasController(jobManager, bus);
    }

    @Singleton
    @Provides
    public PreferenceWrapper providePreferenceWrapper() {
        return new PreferenceWrapper();
    }
}
