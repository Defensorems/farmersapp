package com.farmersapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());

    private DateUtils() {
        // Приватный конструктор для предотвращения создания экземпляров
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        return DATE_FORMAT.format(date);
    }

    public static String formatDateTime(Date date) {
        if (date == null) {
            return "";
        }
        return DATE_TIME_FORMAT.format(date);
    }

    public static Date parseDate(String dateString) {
        try {
            return DATE_FORMAT.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parseDateTime(String dateTimeString) {
        try {
            return DATE_TIME_FORMAT.parse(dateTimeString);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isToday(Date date) {
        if (date == null) {
            return false;
        }

        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return sdf.format(date).equals(sdf.format(today));
    }

    public static boolean isFuture(Date date) {
        if (date == null) {
            return false;
        }

        return date.after(new Date());
    }

    public static boolean isPast(Date date) {
        if (date == null) {
            return false;
        }

        return date.before(new Date());
    }
}