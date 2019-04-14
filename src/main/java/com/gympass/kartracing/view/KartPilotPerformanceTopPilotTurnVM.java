package com.gympass.kartracing.view;

import java.time.LocalTime;

public class KartPilotPerformanceTopPilotTurnVM {
    private int pilotId;
    private String pilotName;
    private LocalTime bestTurnTime;

    public KartPilotPerformanceTopPilotTurnVM(int id, String name, LocalTime l) {
        this.pilotId = id; this.pilotName = name; this.bestTurnTime = l;
    }

    public int getPilotId() {
        return pilotId;
    }

    public String getPilotName() {
        return pilotName;
    }

    public LocalTime getBestTurnTime() {
        return bestTurnTime;
    }
}
