package com.mycompany.oop_project;

import java.util.ArrayList;
import java.util.Random;

public class Restaurant {
    private Admin admin;
    private Cashier cashier;
    private ArrayList<Customer> customers;
    private ArrayList<MenuItem> menu;
    private ArrayList<Order> orders;

    public Restaurant() {
        this.customers = new ArrayList<>();
        this.menu = new ArrayList<>();
        this.orders = new ArrayList<>();
        
        this.admin = new Admin("admin", "admin");
        this.cashier = new Cashier(1, "John Doe", "0599000000", "cashier", "cashier");
        
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
        int lastNum = 1000;
        for (MenuItem item : menu) {
            if (item.getId().startsWith(typePrefix)) {
                try {
                    int num = Integer.parseInt(item.getId().substring(1));
                    if (num > lastNum) {
                        lastNum = num;
                    }
                } catch (Exception e) {
                    // Ignore parsing errors
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