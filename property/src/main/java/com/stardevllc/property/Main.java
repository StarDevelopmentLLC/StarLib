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
                booleanProperty.setValue(true);
            } else if (line.equalsIgnoreCase("false")) {
                booleanProperty.setValue(false);
            } else {
                System.out.println("Invalid input");
            }
        }
    }
}