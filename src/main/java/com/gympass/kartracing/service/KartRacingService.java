package com.gympass.kartracing.service;

import com.gympass.kartracing.model.KartPilot;
import com.gympass.kartracing.model.KartPilotPerformance;
import com.gympass.kartracing.model.KartRacing;
import com.gympass.kartracing.model.KartTurn;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Objects;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class KartRacingService {
    @Value("${kartLineRegex}")
    String kartLineRegex;

    private KartRacing kartRacing;

    public KartRacing getKartRacing() {
        return kartRacing;
    }

    public KartRacing loadKartRacingFile(String fn, KartRacing kr) {
        initialiazeKartRacing(kr);

        try {
            File file = ResourceUtils.getFile("classpath:" + fn);

            Files.lines(Paths.get(file.toURI())).forEach(this::populateKartRacingFromLine);
        } catch (IOException e) {
            kartRacing.getKartPilotPerformances().clear();
        }

        return kartRacing;
    }

    void initialiazeKartRacing(KartRacing kr) {
        if (null == kartRacing) this.kartRacing = new KartRacing();

        if (null != kartRacing.getKartPilotPerformances() && kartRacing.getKartPilotPerformances().size() > 0) kartRacing.getKartPilotPerformances().clear();
    }

    private void populateKartRacingFromLine(String line) {
        Pattern pattern = Pattern.compile(kartLineRegex);
        Matcher matcher = pattern.matcher(line);

        matcher.results().forEach(this::populateKartRacingWithPilotPerformanceInformation);
    }

    private void populateKartRacingWithPilotPerformanceInformation(MatchResult mr) {
        try {
            populateKartRacingWithPilotPerformanceInformation(Objects.requireNonNull(createKartPilotPerformanceFromMatcher(mr)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    KartPilotPerformance createKartPilotPerformanceFromMatcher(MatchResult mr) {
        if (mr.groupCount() > 0 && mr.groupCount() != 6) return null;

        LocalTime hora = LocalTime.parse(mr.group(1));
        int pilotId = Integer.parseInt(mr.group(2));
        String pilotName = mr.group(3);
        int numVolta = Integer.parseInt(mr.group(4));
        LocalTime tempoVolta = LocalTime.parse(mr.group(5));
        double velocidadeMedia = Double.parseDouble(mr.group(6));

        KartPilot p = new KartPilot(pilotName, pilotId);
        KartTurn t = new KartTurn(hora, numVolta, tempoVolta, velocidadeMedia);

        return new KartPilotPerformance(p, t);
    }

    private void populateKartRacingWithPilotPerformanceInformation(KartPilotPerformance kartPilotPerformance) throws Exception {
        if (kartPilotIsAlreadyInKartRacing(kartPilotPerformance.getKartPilot())) mergeKartPilotPerfomanceIntoKartRacing(kartPilotPerformance);
        else addKartPilotPerformanceIntoKartRacing(kartPilotPerformance);
    }

    void addKartPilotPerformanceIntoKartRacing(KartPilotPerformance kpp) {
        if (null != kpp) kartRacing.getKartPilotPerformances().add(kpp);
    }

    void mergeKartPilotPerfomanceIntoKartRacing(KartPilotPerformance kpp) {
        if (null == kpp) return;

        KartPilotPerformance kartPilotPerformance = getKartPilotPerformanceFromKartRacingByKartPilot(kpp.getKartPilot());

        if (null == kartPilotPerformance) {
            addKartPilotPerformanceIntoKartRacing(kpp);
            kartPilotPerformance = getKartPilotPerformanceFromKartRacingByKartPilot(kpp.getKartPilot());
        }

        kartPilotPerformance.getKartTurns().addAll(kpp.getKartTurns());

        kartRacing.getKartPilotPerformances().remove(kpp);
        kartRacing.getKartPilotPerformances().add(kartPilotPerformance);
    }

    KartPilotPerformance getKartPilotPerformanceFromKartRacingByKartPilot(KartPilot kp) {
        if (null == kp || null == kartRacing || null == kartRacing.getKartPilotPerformances() || 0 == kartRacing.getKartPilotPerformances().size()) return null;

        return this.kartRacing.getKartPilotPerformances().stream().filter(k -> k.getKartPilot().equals(kp)).findAny().orElse(null);
    }

    boolean kartPilotIsAlreadyInKartRacing(KartPilot kp) {
        if (null == kp || null == kartRacing || null == kartRacing.getKartPilotPerformances() || 0 == kartRacing.getKartPilotPerformances().size()) return false;

        return this.kartRacing.getKartPilotPerformances().stream().anyMatch(k -> k.getKartPilot().equals(kp));
    }
}
