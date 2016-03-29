package kz.test.spravochnik.data.model.hospital;

import java.util.List;

import kz.test.spravochnik.bus.ApiFailureEvent;
import kz.test.spravochnik.data.api.ApiException;

public class HospitalEvents {

    public static class GetHospitalSuccess {

        private final List<Hospital> mHospitals;

        public GetHospitalSuccess(List<Hospital> hospitals) {
            this.mHospitals = hospitals;
        }

        public List<Hospital> getHospitals() {
            return this.mHospitals;
        }
    }

    public static class GetHospitalFailed extends ApiFailureEvent {
        public GetHospitalFailed(ApiException apiException) {
            super(apiException);
        }
    }
}
