package com.gympass.kartracing.model;

import java.util.*;

public class KartPilotPerformance {
    private KartPilot kartPilot;
    private List<KartTurn> kartTurns;

    public KartPilotPerformance(KartPilot p, KartTurn t) {
        this.kartPilot = p; this.kartTurns = new ArrayList<>(Collections.singletonList(t));
    }

    public List<KartTurn> getKartTurns() {
        return kartTurns;
    }
    public KartPilot getKartPilot() {
        return kartPilot;
    }

    void addKartTurn(KartTurn t) {
        if (kartTurns.size() == 0) kartTurns =  new ArrayList<>(Collections.singletonList(t));
        else kartTurns.add(t);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KartPilotPerformance that = (KartPilotPerformance) o;
        return kartPilot.equals(that.kartPilot) && kartTurns.equals(that.kartTurns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kartPilot, kartTurns);
    }
}
