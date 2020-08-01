package com.example.project;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;

import java.util.regex.*;
import java.io.*;

public class TestMain{
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private String getOutput() {
        return testOut.toString();
    }
    @BeforeEach
    public void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }
    @AfterEach
    public void tearDown() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }
    @Test
    public void testLow()
    {
        int guess = 0;
        String expectedString = "Too low!\n";
        String testString = "" + guess;
        for (int i = 1; i < 101; i++){
            testString += " " + i;
            expectedString += "Too low!\n";
        }
        provideInput(testString);
        Main.main(new String[0]);
        String actual = getOutput();
        Pattern p = Pattern.compile("^(.*)Too low!.*$", Pattern.DOTALL);
        Matcher m = p.matcher(actual);
        assertTrue(m.find(), "Expected string matching '^Too low!'. Got: "+actual);


        p = Pattern.compile("^(.*)You got it!$", Pattern.DOTALL);
        m = p.matcher(actual);
        assertTrue(m.find(), "Expected string matching 'You got it!$'. Got: "+actual);
    }
    @Test
    public  void testHigh(){
        String expectedString = "Too high!\n";
        String testString = "101";
        for (int i = 100; i > 0; i--){
            testString += " " + i;
            expectedString += "Too high!\n";
        }
        provideInput(testString);
        Main.main(new String[0]);
        String actual = getOutput();
        Pattern p = Pattern.compile("^(.*)Too high!.*$", Pattern.DOTALL);
        Matcher m = p.matcher(actual);
        assertTrue(m.find(),"Expected string matching '^Too high!'. Got: "+actual);


        p = Pattern.compile("^(.*)You got it!$", Pattern.DOTALL);
        m = p.matcher(actual);
        assertTrue(m.find(),"Expected string matching 'You got it!$'. Got: "+actual);
    }
}
