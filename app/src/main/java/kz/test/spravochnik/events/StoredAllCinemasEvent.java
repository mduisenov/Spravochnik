package kz.test.spravochnik.events;

import java.util.List;

import kz.test.spravochnik.data.model.cinema.Cinema;

public class StoredAllCinemasEvent {
    private List<Cinema> cinemas;
    private boolean cached;

    public StoredAllCinemasEvent(List<Cinema> cinemas, boolean cached) {
        this.cinemas = cinemas;
        this.cached = cached;
    }

    public List<Cinema> getCinemas() {
        return this.cinemas;
    }

    public boolean isCached() {
        return this.cached;
    }
}
