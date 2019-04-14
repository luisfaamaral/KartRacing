package com.gympass.kartracing.utils;

import java.time.LocalTime;

public class KartLocalTimeUtils {
    public static LocalTime addLocalTime(LocalTime a, LocalTime b) {
        if (null == a) return b;
        if (null == b) return a;

        return a.plusNanos(b.getNano()).plusSeconds(b.getSecond()).plusMinutes(b.getMinute()).plusHours(b.getHour());
    }
}
