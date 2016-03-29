package kz.test.spravochnik.ui.main.cinema;

import javax.inject.Inject;

import kz.test.spravochnik.SpravochnikApp;
import kz.test.spravochnik.controllers.cinema.CinemasController;
import kz.test.spravochnik.ui.BaseListFragment;


public class BaseCinemaFragment extends BaseListFragment {

    @Inject
    protected CinemasController cinemasController;

    protected void onInject() {
        SpravochnikApp.get().component().inject(this);
    }
}
