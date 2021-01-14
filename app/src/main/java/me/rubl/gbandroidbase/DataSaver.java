package me.rubl.gbandroidbase;

public class DataSaver {

    private static DataSaver instance;

    private long reloadCounter = 0;

    private DataSaver() {}

    public static DataSaver getInstance() {
        if (instance == null) instance = new DataSaver();
        return instance;
    }

    public long getReloadCounter() {
        return reloadCounter;
    }

    public void setReloadCounter(long reloadCounter) {
        this.reloadCounter = reloadCounter;
    }
}
