package de.up.sede;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.BeforeClass;
/*
 * A rudimentary test case.
 */
import org.junit.Test;



public class Rudimentary {

    @Test public void testTrue() {
        assertTrue("abc".equalsIgnoreCase("ABC"));
    }
}
