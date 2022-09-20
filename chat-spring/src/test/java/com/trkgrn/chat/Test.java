package com.trkgrn.chat;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        Date temp = new Date(2022, 9, 18);
        Date temp2 = new Date(2022, 9, 19);
        long seconds = ( temp2.getTime() - temp.getTime() ) / (1000*60);
        System.out.println(seconds);
        System.out.println(TimeUnit.HOURS.toSeconds(1));
    }
}
