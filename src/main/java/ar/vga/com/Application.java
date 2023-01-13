package ar.vga.com;

import ar.vga.com.mapper.H2;

public class Application {
    public static void main(String[] a) {
        H2.makeConnection();
    }
}
