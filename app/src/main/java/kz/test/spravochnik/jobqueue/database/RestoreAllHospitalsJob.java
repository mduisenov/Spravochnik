package kz.test.spravochnik.jobqueue.database;

import java.util.List;

import kz.test.spravochnik.controllers.hospital.HospitalController;
import kz.test.spravochnik.data.model.Columns;
import kz.test.spravochnik.data.model.hospital.Hospital;
import timber.log.Timber;

public class RestoreAllHospitalsJob extends BaseDBJob {

    private HospitalController hospitalController;

    public RestoreAllHospitalsJob(HospitalController hospitalController) {
        this.hospitalController = hospitalController;
        Timber.tag("RestoreAllHospitalsJob");
    }

    @Override
    public void onRun() throws Throwable {
        Timber.d("RestoreAllHospitalsJob.onRun...");

        List<Hospital> hospitals = databaseHelper.getHospitalDao().queryBuilder().orderBy(Columns.HOSPITAL_ID, false).query(); //
        hospitalController.restoreHospitals(hospitals);
//        eventBus.post(new StoredAllCinemasEvent(list, cached));

    }

    @Override
    protected void onCancel() {
        Timber.d("RestoreAllHospitalsJob.onCancel...");
    }
}
