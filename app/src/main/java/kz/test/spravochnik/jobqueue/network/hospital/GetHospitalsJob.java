package kz.test.spravochnik.jobqueue.network.hospital;

import java.util.List;

import kz.test.spravochnik.controllers.hospital.HospitalController;
import kz.test.spravochnik.data.model.hospital.Hospital;
import timber.log.Timber;

public class GetHospitalsJob extends BaseHospitalJob {

    private final HospitalController hospitalController;

    public GetHospitalsJob(HospitalController hospitalController) {
        this.hospitalController = hospitalController;
    }

    public void onRun() throws Throwable {
        Timber.d("onRun() ");
        List<Hospital> hospitals;
        hospitals = services.getHospitals();

        if (hospitals != null) {
            for (Hospital hospital : hospitals) {
                Timber.d(hospital.toString());
                databaseHelper.getHospitalDao().createOrUpdate(hospital);
            }
        }
        hospitalController.handleGetHospitalsSuccess(hospitals);
//        hospitalController.handleGetHospitalsSuccess(this.services.getHospitals());
    }

    protected void onCancel() {
        super.onCancel();
        Timber.d("onCancel()");
        hospitalController.handleGetHospitalError(getApiException());
    }
}
