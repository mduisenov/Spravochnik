package kz.test.spravochnik.di;

import kz.test.spravochnik.SpravochnikApp;
import kz.test.spravochnik.controllers.cinema.CinemasController;
import kz.test.spravochnik.controllers.hospital.HospitalController;
import kz.test.spravochnik.data.model.db.DatabaseHelper;
import kz.test.spravochnik.data.prefs.PreferenceWrapper;
import kz.test.spravochnik.jobqueue.database.BaseDBJob;
import kz.test.spravochnik.jobqueue.network.cinema.BaseCinemaJob;
import kz.test.spravochnik.jobqueue.network.hospital.BaseHospitalJob;
import kz.test.spravochnik.sidemenu.SideMenuDrawerFragment;
import kz.test.spravochnik.ui.main.MainActivity;
import kz.test.spravochnik.ui.main.cinema.BaseCinemaFragment;
import kz.test.spravochnik.ui.main.cinema.adapter.CinemaViewHolder;
import kz.test.spravochnik.ui.main.hospital.BaseHospitalFragment;
import kz.test.spravochnik.ui.main.hospital.adapter.HospitalViewHolder;

public interface SpravochnikGraph {

    void inject(SpravochnikApp playerApp);

    void inject(BaseDaggerActivity baseDaggerActivity);

    void inject(BaseDaggerBroadcastReceiver baseDaggerBroadcastReceiver);

    void inject(BaseDaggerDialogFragment baseDaggerDialogFragment);

    void inject(BaseDaggerFragment baseDaggerFragment);

    void inject(BaseDaggerService baseDaggerService);

    void inject(BaseHospitalJob baseHospitalJob);

    void inject(BaseCinemaJob baseCinemaJob);

    void inject(MainActivity mainActivity);

    void inject(CinemaViewHolder cinemaViewHolder);

    void inject(HospitalViewHolder hospitalViewHolder);

    void inject(BaseCinemaFragment baseCinemaFragment);

    void inject(BaseHospitalFragment baseHospitalFragment);

    void inject(SideMenuDrawerFragment sideMenuDrawerFragment);

    void inject(DatabaseHelper databaseHelper);

    void inject(BaseDBJob baseDBJob);

    void inject(PreferenceWrapper preferenceWrapper);

    void inject(HospitalController hospitalController);

    void inject(CinemasController cinemasController);
}
