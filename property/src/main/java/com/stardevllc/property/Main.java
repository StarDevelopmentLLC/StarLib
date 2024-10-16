package com.stardevllc.property;

import java.util.Scanner;

public final class Main {
    public static void main(String[] args) {
        BooleanProperty booleanProperty = new BooleanProperty();
        booleanProperty.addListener(e -> System.out.println("Boolean Property Changed from " + e.oldValue() + " to " + e.newValue()));

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a value: ");
        String line;
        while ((line = scanner.nextLine()) != null) {
            if (line.equals("exit")) {
                break;
            }

            if (line.equalsIgnoreCase("true")) {
                long start = System.currentTimeMillis();
                booleanProperty.setValue(true);
                long end = System.currentTimeMillis();
                System.out.println("Time: " + (end - start));
            } else if (line.equalsIgnoreCase("false")) {
                long start = System.currentTimeMillis();
                booleanProperty.setValue(false);
                long end = System.currentTimeMillis();
                System.out.println("Time: " + (end - start));
            } else {
                System.out.println("Invalid input");
            }
        }
    }
}