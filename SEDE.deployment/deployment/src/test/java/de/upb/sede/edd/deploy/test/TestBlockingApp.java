package de.upb.sede.edd.deploy.test;

public class TestBlockingApp {

    public static void main(String[] args) throws InterruptedException {
        while(true) {
            Thread.sleep(10000);
        }
    }

}
