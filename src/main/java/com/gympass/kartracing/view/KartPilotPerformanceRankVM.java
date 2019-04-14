package com.gympass.kartracing.view;

import java.time.LocalTime;

public class KartPilotPerformanceRankVM {
    private int rank;
    private int pilotId;
    private String pilotName;
    private int turns;
    private LocalTime totalTime;

    public KartPilotPerformanceRankVM(int id, String name, int t, LocalTime l) {
        this.pilotId = id; this.pilotName = name; this.turns = t; this.totalTime = l;
    }


    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public LocalTime getTotalTime() {
        return totalTime;
    }

    public int getPilotId() {
        return pilotId;
    }

    public String getPilotName() {
        return pilotName;
    }

    public int getTurns() {
        return turns;
    }
}
