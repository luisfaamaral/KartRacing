package com.gympass.kartracing.service;

import com.gympass.kartracing.model.KartRacing;
import com.gympass.kartracing.model.KartTurn;
import com.gympass.kartracing.utils.KartLocalTimeUtils;
import com.gympass.kartracing.view.KartPilotPerformanceRankVM;
import com.gympass.kartracing.view.KartPilotPerformanceTopPilotTurnVM;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class KartRacingStatsService {
    private KartRacing kartRacing;

    public List<KartPilotPerformanceRankVM> kartRacingStatsRank(KartRacing kr) {
        if (null == kr) return null;

        this.kartRacing = kr;

        List<KartPilotPerformanceRankVM> vm = kartRacing.getKartPilotPerformances().stream().distinct().map(k -> new KartPilotPerformanceRankVM(k.getKartPilot().getPilotId(),
                k.getKartPilot().getPilotName(), k.getKartTurns().size(), getTotalTime(k.getKartTurns()))).sorted(this::rankCustomCompare).collect(Collectors.toList());

        initializeRank(vm);

        return vm;
    }

    private int rankCustomCompare(KartPilotPerformanceRankVM k1, KartPilotPerformanceRankVM k2) {
        int res = Integer.compare(k2.getTurns(), k1.getTurns()); // desc - so invert the compare

        if (res == 0) res = Double.compare(k1.getTotalTime().toNanoOfDay(), k2.getTotalTime().toNanoOfDay());

        return res;
    }

    private void initializeRank(List<KartPilotPerformanceRankVM> kppVM) {
        AtomicInteger atomicInteger = new AtomicInteger(1);

        kppVM.forEach(k -> k.setRank(atomicInteger.getAndIncrement()));
    }

    private LocalTime getTotalTime(List<KartTurn> kartTurns) {
        return kartTurns.stream().map(KartTurn::getTempo).reduce(null,KartLocalTimeUtils::addLocalTime);
    }

    public List<KartPilotPerformanceTopPilotTurnVM> kartRacingStatsTopPilotsTurn(KartRacing kr) {
        if (null == kr) return null;

        this.kartRacing = kr;

        return kartRacing.getKartPilotPerformances().stream().distinct().map(k -> new KartPilotPerformanceTopPilotTurnVM(k.getKartPilot().getPilotId(),
                k.getKartPilot().getPilotName(), getBestTurn(k.getKartTurns()))).collect(Collectors.toList());
    }

    private LocalTime getBestTurn(List<KartTurn> kartTurns) {
        return kartTurns.stream().map(KartTurn::getTempo).sorted().findFirst().orElse(null);
    }
}
