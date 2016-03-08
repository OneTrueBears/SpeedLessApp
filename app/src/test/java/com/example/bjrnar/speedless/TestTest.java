package com.example.bjrnar.speedless;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest  {

    /**
     * I wonder if we need to make it ... extends junit.framework.TestCase
     *
     * or if api currently only need an imprt and the use of '@' mentions.
     * For this, see documentation screenshots provided in the otherwise example-less lecture
     * folio on Testing
     */

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals("Yo, this is a test message YAY SUPER TEST", 4, 2 + 2);
    }
}