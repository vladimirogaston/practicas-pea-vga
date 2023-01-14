package ar.vga.com.domain;

import java.time.LocalTime;

public class IDGenerator {
    public static Long nextID(){
        LocalTime localTime = LocalTime.now();
        return localTime.toNanoOfDay();
    }
}
