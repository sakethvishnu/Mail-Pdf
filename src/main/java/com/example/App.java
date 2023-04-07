package com.example;

import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println(Arrays.toString(new Mail().createPdf()));
        new Mail().sendEmail("saketh4532@gmail.com");
    }
}
