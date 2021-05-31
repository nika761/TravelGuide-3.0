package travelguideapp.ge.travelguide.helper;

import android.os.Build;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import travelguideapp.ge.travelguide.base.MainApplication;

public class HelperDate {

    private static final String DATE_ITEM_DIVIDER = "-";

    public static String getDateStringFormat(int year, int month, int dayOfMonth) {
        return year + DATE_ITEM_DIVIDER + getStringValueOfMonth(month) + DATE_ITEM_DIVIDER + getStringValueOfDay(dayOfMonth);
    }

    public static String getDateStringFormat(Calendar calendar) {
        return getDateStringFormat(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static String getDateStringFormat(long timeInMilli) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMilli);
        return getDateStringFormat(cal);
    }

    public static String getDateFromMillis(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000);
        return DateFormat.format("dd-MM-yyyy", calendar).toString();
    }

    public static Calendar getCalFromDate(int year, int month, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return cal;
    }

    public static long getDateInMilliFromDate(int year, int month, int dayOfMonth) {
        return getCalFromDate(year, month, dayOfMonth).getTimeInMillis();
    }

    public static long getTimeInMillisFromServerDateString(String dateStr) throws ParseException {
        if (dateStr == null || dateStr.isEmpty()) {
            return -1;
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = format.parse(dateStr);
            return date.getTime();
        }
    }

    private static String getStringValueOfMonth(int number) {
        number++;
        if (number < 10)
            return "0" + number;
        else
            return "" + number;
    }

    private static String getStringValueOfDay(int number) {
        if (number < 10)
            return "0" + number;
        else
            return "" + number;
    }

    public static boolean checkAgeOfUser(int birthYear, int birthMonth, int birthDay) {

        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH);
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate currentDate = LocalDate.of(currentYear, currentMonth + 1, currentDay);
            LocalDate birthDate = LocalDate.of(birthYear, birthMonth, birthDay);

            if ((birthDate != null) && (currentDate != null)) {
                int userAge = Period.between(birthDate, currentDate).getYears();
                return userAge < MainApplication.AGE_RESTRICTION ? false : true;
            }

        } else {
            int userAge = currentYear - birthYear;
            return userAge < MainApplication.AGE_RESTRICTION ? false : true;
        }

        return false;
    }
}
