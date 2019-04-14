package com.gympass.kartracing.controler;

import com.gympass.kartracing.model.KartRacing;
import com.gympass.kartracing.service.KartRacingService;
import com.gympass.kartracing.service.KartRacingStatsService;
import com.gympass.kartracing.view.KartPilotPerformanceRankVM;
import com.gympass.kartracing.view.KartPilotPerformanceTopPilotTurnVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class KartRacingController {
    private String welcomeMessage;
    private KartRacingService kartRacingService;
    private KartRacingStatsService kartRacingStatsService;

    // Global as we dont have database
    private KartRacing kartRacing;

    @Autowired
    KartRacingController(@Value("${welcome_message}") String wm, KartRacingService krs, KartRacingStatsService krss) {
        this.welcomeMessage = wm;
        this.kartRacingService = krs;
        this.kartRacingStatsService = krss;
    }

    @RequestMapping("/")
    @ResponseBody
    String root() {
        StringBuilder sb = new StringBuilder();
        sb.append(welcomeMessage).append("<br /><br />");
        sb.append("Load Kart Racing File: /loadKartRacingFile/{fileName}").append("<br />");
        sb.append("Ranking: /kartRacingStats/ranking").append("<br />");
        sb.append("Ranking: /kartRacingStats/topPilotsTurn").append("<br />");

        return sb.toString();
    }

    @RequestMapping("/loadKartRacingFile/{fileName}")
    @ResponseBody
    String loadKartRacing(@PathVariable String fileName) {
        kartRacing = kartRacingService.loadKartRacingFile(fileName, kartRacing);

        return null == kartRacing ? "Could not load Kart Racing." : "Kart Racing successfully loaded.";
    }

    @RequestMapping("/kartRacingStats/ranking")
    @ResponseBody
    List<KartPilotPerformanceRankVM> kartRacingStatsRank() {
        return kartRacingStatsService.kartRacingStatsRank(kartRacing);
    }

    @RequestMapping("/kartRacingStats/topPilotsTurn")
    @ResponseBody
    List<KartPilotPerformanceTopPilotTurnVM> kartRacingStatsTopPilotsTurn() {
        return kartRacingStatsService.kartRacingStatsTopPilotsTurn(kartRacing);
    }
}
