package kienpd.com.mtee.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtil {

    public static final int SECOND_BY_MILISECONDS = 1000;
    public static final int MINUTE_BY_SECONDS = 60;
    public static final int HOUR_BY_MINUTES = 60;
    public static final int DAY_BY_HOURS = 24;
    public static final int WEEK_BY_DAYS = 7;
    public static final int MONTH_BY_WEEK = 4;
    public static final int YEAR_BY_MONTH = 12;

    public static final long DEFAULT_EPOCH_TIME = 946684800;
    public static final String DATE_TIME_FORMAT_STRING = "yyyy/MM/dd";
    public static final String DATE_FORMAT_STRING = "dd/MM/yyyy";
    public static final String DATE_FORMAT_STRING_VN = "dd/MM/yyyy HH:mm";
    private static final String TIME_SPAN_BY_MINUTES_SECONDS_FORMATTER = "%02d:%02d";

    public static Date fromEpoch(long epoch) {
        return new Date(epoch * 1000L);
    }

    public static String formatDateTime(Date date) {
        DateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT_STRING, Locale.getDefault());
        return format.format(date);
    }

    public static String formatDate(long timeInMilies) {
        Date d = new Date(timeInMilies);
        DateFormat format = new SimpleDateFormat(DATE_FORMAT_STRING, Locale.getDefault());
        return format.format(d);
    }

    public static String formatDate(Date date) {
        DateFormat format = new SimpleDateFormat(DATE_FORMAT_STRING, Locale.getDefault());
        return format.format(date);
    }

    public static String getMMSSFromMiliseconds(int miliseconds) {
        int totalSecs = miliseconds / SECOND_BY_MILISECONDS;
        return String.format(TIME_SPAN_BY_MINUTES_SECONDS_FORMATTER,
                totalSecs / MINUTE_BY_SECONDS, totalSecs % MINUTE_BY_SECONDS);
    }


    public static Date miliTimeToDate(long timeInMilies) {
        Date d = new Date(timeInMilies);
        DateFormat format = new SimpleDateFormat(DATE_FORMAT_STRING, Locale.getDefault());
        return d;
    }

    public static String getStringDateFromMiliseconds(long timeInMilies) {
        Date date = new Date(timeInMilies);
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        String dateText = df2.format(date);
        return dateText;
    }

    public static String formatDateVN(long timeInMilies) {
        Date d = new Date(timeInMilies);
        DateFormat format = new SimpleDateFormat(DATE_FORMAT_STRING_VN, Locale.getDefault());
        return format.format(d);
    }
}
