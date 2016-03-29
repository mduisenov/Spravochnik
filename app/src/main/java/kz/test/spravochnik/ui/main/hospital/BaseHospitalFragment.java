package kz.test.spravochnik.ui.main.hospital;

import javax.inject.Inject;

import kz.test.spravochnik.SpravochnikApp;
import kz.test.spravochnik.controllers.hospital.HospitalController;
import kz.test.spravochnik.ui.BaseListFragment;


public class BaseHospitalFragment extends BaseListFragment {

    @Inject
    protected HospitalController hospitalController;

    protected void onInject() {
        SpravochnikApp.get().component().inject(this);
    }
}
