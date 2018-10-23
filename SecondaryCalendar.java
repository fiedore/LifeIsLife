package lifeislife;

import lifeislife.data.constants.Month;

public class SecondaryCalendar {

    private static int day;
    private static Month month;
    private static int year;

    public SecondaryCalendar() {
    }

    public void setDate(int day, int month, int year) {
        SecondaryCalendar.year = year;
                SecondaryCalendar.month = Month.values()[month - 1];
        SecondaryCalendar.day = day;
    }

    public static void setYear(int year) {
        SecondaryCalendar.year = year;
        Month.isItLeapYear(year % 4 == 0);
    }

    public static void setMonth(int month) {
        SecondaryCalendar.month = Month.values()[month - 1];
    }

    public static void setDay(int day) {
        SecondaryCalendar.day = day;
    }

    public static void liveDay() {
        if (day + 1 > month.getDays()) {
            day = 1;
            if (month.ordinal() >= 11) {
                setMonth(1);
                setYear(year + 1);
            } else {
                setMonth(month.ordinal() + 1);
            }
        } else {
            day++;
        }
    }

    public static void liveDays(int days) {
        for (int i = 0; i < days; i++) {
            liveDay();
        }
    }

    @Override
    public String toString() {
        return year + "-" + month + "-" + day;
    }

}
