package com.gympass.kartracing.model;

import java.util.Objects;

public class KartPilot {
    private String pilotName;
    private int pilotId;

    public KartPilot(String name, int id) {
        this.pilotName = name;
        this.pilotId = id;
    }

    public String getPilotName() {
        return pilotName;
    }

    public int getPilotId() {
        return pilotId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KartPilot kartPilot = (KartPilot) o;
        return pilotId == kartPilot.pilotId && pilotName.equals(kartPilot.pilotName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pilotName, pilotId);
    }
}
