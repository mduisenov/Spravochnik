package kz.test.spravochnik.events;


import kz.test.spravochnik.data.model.cinema.Cinema;

public class CinemasInvalidatedEvent {
    private Cinema cinema;

    public CinemasInvalidatedEvent(Cinema cinema) {
        this.cinema = cinema;
    }

    public Cinema getCinema() {
        return this.cinema;
    }
}
