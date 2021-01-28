package me.rubl.gbandroidbase.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static class Strings {

        public static boolean checkCityName(String cityName) {
            Pattern pattern = Pattern.compile("^[a-zа-яA-ZА-Я]+(?:[\\s-][a-zа-яA-ZА-Я]+)*$");
            Matcher matcher = pattern.matcher(cityName);
            return matcher.matches();
        }
    }
}
