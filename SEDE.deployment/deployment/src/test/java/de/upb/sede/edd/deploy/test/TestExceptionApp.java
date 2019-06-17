package de.upb.sede.edd.deploy.test;

public class TestExceptionApp
{
    public static void main(String[] args) throws InterruptedException {
        throw new InterruptedException("Interrupt");
    }
}
