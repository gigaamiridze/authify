package com.cyber.authify.utils.formatter;

import java.time.Duration;

public class DurationFormatter {

    public static String format(Duration duration) {
        long seconds = duration.toSeconds();
        if (seconds < 60) {
            return plural(seconds, "second");
        }

        long minutes = duration.toMinutes();
        if (minutes < 60) {
            return plural(minutes, "minute");
        }

        long hours = duration.toHours();
        if (hours < 24) {
            return plural(hours, "hour");
        }

        long days = duration.toDays();
        return plural(days, "day");
    }

    private static String plural(long value, String unit) {
        return String.format("%d %s%s", value, unit, value == 1 ? "" : "s");
    }
}
