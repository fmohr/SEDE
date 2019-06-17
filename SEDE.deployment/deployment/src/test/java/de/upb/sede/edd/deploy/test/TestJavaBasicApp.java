package de.upb.sede.edd.deploy.test;

import java.io.IOException;

public class TestJavaBasicApp {
    public static void main(String[] args) throws IOException {
        if(args.length == 0) {
            System.exit(11);
        }
        System.out.println(args[0] + " times two is: " + Integer.parseInt(args[0]) * 2);
    }
}
