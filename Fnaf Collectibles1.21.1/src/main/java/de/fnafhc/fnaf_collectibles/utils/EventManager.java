package de.fnafhc.fnaf_collectibles.utils;

import java.time.LocalDate;

public class EventManager {
    private static boolean isBetween(LocalDate start, LocalDate end) {
        LocalDate today = LocalDate.now();
        return !today.isBefore(start) && !today.isAfter(end);
    }

    public static boolean isHalloween() {
        return isBetween(
                LocalDate.of(LocalDate.now().getYear(), 10, 29),
                LocalDate.of(LocalDate.now().getYear(), 11, 2)
        );
    }

    public static boolean isChristmas() {
        return isBetween(
                LocalDate.of(LocalDate.now().getYear(), 12, 22),
                LocalDate.of(LocalDate.now().getYear(), 12, 28)
        );
    }

    public static boolean isValentinesDay() {
        return isBetween(
                LocalDate.of(LocalDate.now().getYear(), 2, 12),
                LocalDate.of(LocalDate.now().getYear(), 2, 16)
        );
    }

    public static boolean isAprilFools() {
        return isBetween(
                LocalDate.of(LocalDate.now().getYear(), 3, 31),
                LocalDate.of(LocalDate.now().getYear(), 4, 3)
        );
    }

    public static boolean isNewYear() {
        LocalDate today = LocalDate.now();
        int year = today.getYear();

        return isBetween(
                LocalDate.of(year - 1, 12, 31),
                LocalDate.of(year, 1, 3)
        );
    }

    public static LocalDate getEasterSunday(int year) {

        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;

        int month = (h + l - 7 * m + 114) / 31;
        int day = ((h + l - 7 * m + 114) % 31) + 1;

        return LocalDate.of(year, month, day);
    }

    public static boolean isEaster() {
        LocalDate easter = getEasterSunday(LocalDate.now().getYear());

        return isBetween(
                easter.minusDays(2),
                easter.plusDays(4)
        );
    }
}
