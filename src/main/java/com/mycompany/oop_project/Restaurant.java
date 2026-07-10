package com.mycompany.oop_project;

import java.util.ArrayList;
import java.util.Random;

public class Restaurant {
    final private Admin admin;
    final private Cashier cashier;
    final private ArrayList<Customer> customers;
    final private ArrayList<MenuItem> menu;
    final private ArrayList<Order> orders;

    public Restaurant() {
        this.customers = new ArrayList<>();
        this.menu = new ArrayList<>();
        this.orders = new ArrayList<>();
        
        this.admin = new Admin(1,"Amir Abu ALhen","0599000000","amir", "amir");
        this.cashier = new Cashier(2, "Omar Abu Alsebah", "0599000000", "omar", "omar");
        
        menu.add(new Food("F1001", "Beef Burger", 12.0, "Main Course"));
        menu.add(new Food("F1002", "Chicken Pizza", 15.0, "Main Course"));
        menu.add(new Drink("D2001", "Coca Cola", 2.0, "Cold Drink"));
    }

    public Admin getAdmin() { return admin; }
    public Cashier getCashier() { return cashier; }
    public ArrayList<Customer> getCustomers() { return customers; }
    public ArrayList<MenuItem> getMenu() { return menu; }
    public ArrayList<Order> getOrders() { return orders; }

    public String generateNextProductId(String typePrefix) {
        int lastNum = 1000; // 4 digits no more no less
        for (MenuItem item : menu) {
            if (item.getId().startsWith(typePrefix)) {
                try {
                    int num = Integer.parseInt(item.getId().substring(1));
                    if (num > lastNum) {
                        lastNum = num;
                    }
                } catch (Exception e) {
                    System.out.println("error"+" " + e);
                }
            }
        }
        return typePrefix + (lastNum + 1);
    }

    public String generateUniqueCustomerUuid() {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random r = new Random();
        while (true) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 3; i++) sb.append(letters.charAt(r.nextInt(letters.length())));
            for (int i = 0; i < 3; i++) sb.append(r.nextInt(10));
            String code = sb.toString();
            
            boolean exists = false;
            for (Customer c : customers) {
                if (c.getCustomerUuid().equals(code)) { exists = true; break; }
            }
            if (!exists) return code;
        }
    }

    public void printMenu() {
        System.out.println(String.format("\n%-10s | %-20s | %-10s | %-15s | %-8s |", "ID", "Name", "Price", "Category/Size", "Type"));
        System.out.println("-------------------------------------------------------------------------");
        for (MenuItem m : menu) {
            System.out.println(m.getItemDetails());
        }
    }
}