package me.rubl.gbandroidbase.events;

import com.squareup.otto.Bus;

public class BusProvider {

    private static Bus instance;

    private BusProvider() {}

    public static Bus getInstance() {
        if (instance == null) instance = new Bus();
        return instance;
    }
}
