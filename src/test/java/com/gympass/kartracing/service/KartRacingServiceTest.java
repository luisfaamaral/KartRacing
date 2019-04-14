package com.gympass.kartracing.service;

import com.gympass.kartracing.model.KartPilot;
import com.gympass.kartracing.model.KartPilotPerformance;
import com.gympass.kartracing.model.KartTurn;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class KartRacingServiceTest {
    private KartRacingService kartRacingService = new KartRacingService();

    @Before
    public void setup() {
        kartRacingService.initialiazeKartRacing(null);
    }

    @Test
    public void createKartPilotPerformanceFromMatcher_ifNullMatcher_thenReturnNull() {
        String line = "AA";

        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(line);

        assertNull(kartRacingService.createKartPilotPerformanceFromMatcher(matcher));
    }

    @Test
    public void createKartPilotPerformanceFromMatcher_ifMatcherWithLessThan6Items_thenReturnNull() {
        String line = "10";

        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(line);

        assertEquals(1, matcher.groupCount());
        assertNull(kartRacingService.createKartPilotPerformanceFromMatcher(matcher));
    }

    @Test
    public void createKartPilotPerformanceFromMatcher_ifMatcherOK_thenReturnKartPilotPerformance() {
        String line = "23:49:08.277  038 - F.MASSA  1  00:01:02.852  44.275";

        Pattern pattern = Pattern.compile("([0-9:.]+) *([0-9]+) - ([A-Za-z.]+) *([0-9]+) *([0-9:.]+) *([0-9.]+)");
        Matcher matcher = pattern.matcher(line);

        matcher.find();

        KartPilot kp = new KartPilot("F.MASSA", 38);
        KartTurn kt = new KartTurn(LocalTime.parse("23:49:08.277"), 1, LocalTime.parse("00:01:02.852"), 44.275);
        KartPilotPerformance kpp = new KartPilotPerformance(kp, kt);

        assertEquals(6, matcher.groupCount());
        assertEquals(kpp, kartRacingService.createKartPilotPerformanceFromMatcher(matcher));
    }

    @Test
    public void addKartPilotPerformanceIntoKartRacing() {
        KartPilot kp = new KartPilot("F.MASSA", 38);
        KartTurn kt = new KartTurn(LocalTime.parse("23:49:08.277"), 1, LocalTime.parse("00:01:02.852"), 44.275);
        KartPilotPerformance kpp = new KartPilotPerformance(kp, kt);

        assertEquals(0, kartRacingService.getKartRacing().getKartPilotPerformances().size());
        kartRacingService.addKartPilotPerformanceIntoKartRacing(kpp);
        assertEquals(1, kartRacingService.getKartRacing().getKartPilotPerformances().size());
    }

    @Test
    public void kartPilotIsAlreadyInKartRacing_ifPilotNotPresent_thenReturnFalse() {
        KartPilot kp = new KartPilot("F.MASSA", 38);
        KartTurn kt = new KartTurn(LocalTime.parse("23:49:08.277"), 1, LocalTime.parse("00:01:02.852"), 44.275);
        KartPilotPerformance kpp = new KartPilotPerformance(kp, kt);

        kartRacingService.addKartPilotPerformanceIntoKartRacing(kpp);

        KartPilot kp1 = new KartPilot("R.BARRICHELLO", 33);
        assertFalse(kartRacingService.kartPilotIsAlreadyInKartRacing(kp1));
    }

    @Test
    public void kartPilotIsAlreadyInKartRacing_ifPilotIsPresent_thenReturnTrue() {
        KartPilot kp = new KartPilot("F.MASSA", 38);
        KartTurn kt = new KartTurn(LocalTime.parse("23:49:08.277"), 1, LocalTime.parse("00:01:02.852"), 44.275);
        KartPilotPerformance kpp = new KartPilotPerformance(kp, kt);

        kartRacingService.addKartPilotPerformanceIntoKartRacing(kpp);

        assertTrue(kartRacingService.kartPilotIsAlreadyInKartRacing(kp));
    }

    @Test
    public void getKartPilotPerformanceFromKartRacingByKartPilot_ifEmptyKartRacing_thenReturnNull() {
        KartPilot kp = new KartPilot("F.MASSA", 38);
        KartTurn kt = new KartTurn(LocalTime.parse("23:49:08.277"), 1, LocalTime.parse("00:01:02.852"), 44.275);
        KartPilotPerformance kpp = new KartPilotPerformance(kp, kt);

        assertNull(kartRacingService.getKartPilotPerformanceFromKartRacingByKartPilot(kp));
    }

    @Test
    public void getKartPilotPerformanceFromKartRacingByKartPilot_ifPilotNotPresent_thenReturnNull() {
        KartPilot kp = new KartPilot("F.MASSA", 38);
        KartTurn kt = new KartTurn(LocalTime.parse("23:49:08.277"), 1, LocalTime.parse("00:01:02.852"), 44.275);
        KartPilotPerformance kpp = new KartPilotPerformance(kp, kt);

        kartRacingService.addKartPilotPerformanceIntoKartRacing(kpp);

        KartPilot kp1 = new KartPilot("R.BARRICHELLO", 33);
        assertNull(kartRacingService.getKartPilotPerformanceFromKartRacingByKartPilot(kp1));

    }

    @Test
    public void getKartPilotPerformanceFromKartRacingByKartPilot_ifPilotIsPresent_thenReturnKartPilot() {
        KartPilot kp = new KartPilot("F.MASSA", 38);
        KartTurn kt = new KartTurn(LocalTime.parse("23:49:08.277"), 1, LocalTime.parse("00:01:02.852"), 44.275);
        KartPilotPerformance kpp = new KartPilotPerformance(kp, kt);

        kartRacingService.addKartPilotPerformanceIntoKartRacing(kpp);

        assertEquals(kpp, kartRacingService.getKartPilotPerformanceFromKartRacingByKartPilot(kp));
    }

    @Test
    public void mergeKartPilotPerfomanceIntoKartRacing() {
        KartPilot kp = new KartPilot("F.MASSA", 38);
        KartTurn kt = new KartTurn(LocalTime.parse("23:49:08.277"), 1, LocalTime.parse("00:01:02.852"), 44.275);
        KartPilotPerformance kpp = new KartPilotPerformance(kp, kt);

        kartRacingService.addKartPilotPerformanceIntoKartRacing(kpp);

        KartPilotPerformance kpp_out = kartRacingService.getKartPilotPerformanceFromKartRacingByKartPilot(kp);
        assertEquals(1, kpp_out.getKartTurns().size());

        KartTurn kt1 = new KartTurn(LocalTime.parse("23:49:08.277"), 2, LocalTime.parse("00:01:02.852"), 44.275);
        KartPilotPerformance kpp1 = new KartPilotPerformance(kp, kt1);

        kartRacingService.mergeKartPilotPerfomanceIntoKartRacing(kpp1);

        kpp_out = kartRacingService.getKartPilotPerformanceFromKartRacingByKartPilot(kp);
        assertEquals(2, kpp_out.getKartTurns().size());
    }
}
