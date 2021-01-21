package me.rubl.gbandroidbase.events;

import me.rubl.gbandroidbase.entities.City;

public class CityChangedEvent {

    private final City newCity;

    public CityChangedEvent(City newCity) {
        this.newCity = newCity;
    }

    public City getNewCity() {
        return newCity;
    }
}
