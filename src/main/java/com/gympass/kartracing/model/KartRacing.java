package com.gympass.kartracing.model;

import java.util.ArrayList;
import java.util.List;

public class KartRacing {
    private List<KartPilotPerformance> kartPilotPerformances;

    public List<KartPilotPerformance> getKartPilotPerformances() {
        return kartPilotPerformances;
    }

    public KartRacing() {
        this.kartPilotPerformances = new ArrayList<>();
    }
}
