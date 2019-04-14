package com.gympass.kartracing.model;

import java.time.LocalTime;
import java.util.Objects;

public class KartTurn {
    private LocalTime hora;
    private int numero;
    private LocalTime tempo;
    private double velMedia;

    public KartTurn(LocalTime hora, int numero, LocalTime tempo, double velMedia) {
        this.hora = hora;
        this.numero = numero;
        this.tempo = tempo;
        this.velMedia = velMedia;
    }

    public LocalTime getHora() {
        return hora;
    }

    public int getNumero() {
        return numero;
    }

    public LocalTime getTempo() {
        return tempo;
    }

    public double getVelMedia() {
        return velMedia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KartTurn kartTurn = (KartTurn) o;
        return numero == kartTurn.numero && Double.compare(kartTurn.velMedia, velMedia) == 0 &&
                hora.equals(kartTurn.hora) && tempo.equals(kartTurn.tempo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hora, numero, tempo, velMedia);
    }
}
