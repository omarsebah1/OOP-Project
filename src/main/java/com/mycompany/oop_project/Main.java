package com.mycompany.oop_project;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n==================================");
            System.out.println("  WELCOME TO RESTAURANT SYSTEM   ");
            System.out.println("==================================");
            System.out.print("Username: ");
            String user = sc.nextLine().trim();
            System.out.print("Password: ");
            String pass = sc.nextLine().trim();

            if (user.equals(restaurant.getAdmin().getUsername()) && pass.equals(restaurant.getAdmin().getPassword())) {
                restaurant.getAdmin().showDashboard(restaurant, sc);
            } else if (user.equals(restaurant.getCashier().getUsername()) && pass.equals(restaurant.getCashier().getPassword())) {
                restaurant.getCashier().showDashboard(restaurant, sc);
            } else {
                System.out.println("Invalid credentials! Try again.");
            }
        }
    }
}