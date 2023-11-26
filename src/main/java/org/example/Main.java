package org.example;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        MainWorker mw = new MainWorker(args);
        mw.process();
    }
}