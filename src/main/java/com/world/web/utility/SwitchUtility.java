package com.world.web.utility;

public class SwitchUtility {

    public static void main(String[] args) {

        System.out.println("Week Day Name :: " +fetchWeekName("MON"));
    }


    /**
     *
     * @param days as Week days name
     * @return String
     */
    public static String fetchWeekName(String days) {
        return switch(days) {
            case "MON", "TUE", "WED", "THU", "FRI" -> "Weekday";
            case "SAT", "SUN" -> "Weekend";
            default -> "Invalid";
        };
    }
}
