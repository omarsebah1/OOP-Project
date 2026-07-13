package com.mycompany.oop_project;

import java.util.ArrayList;
import java.util.Random;
import java.io.*;

public class Restaurant {

    private final Admin admin;
    private final Cashier cashier;
    private final ArrayList<Customer> customers;
    private final ArrayList<MenuItem> menu;
    private final ArrayList<Order> orders;

    private String menuFile;
    private String customerFile;

    public Restaurant() {
        this.customers = new ArrayList<>();
        this.menu = new ArrayList<>();
        this.orders = new ArrayList<>();

        this.admin = new Admin(1, "AMIR ABUALHIN", "0567850098", "amir", "amir");
        this.cashier = new Cashier(2, "Omar Abu Alsebah", "0599000000", "omar", "omar");

        initializePaths();
        loadMenuFromFile();
        loadCustomersFromFile();
    }

    public Admin getAdmin() {
        return admin;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<MenuItem> getMenu() {
        return menu;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    private void initializePaths() {
        try {
            String userDir = System.getProperty("user.dir");
            String targetPath = userDir + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "mycompany" + File.separator + "oop_project" + File.separator + "data";

            File dataDir = new File(targetPath);
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }

            this.menuFile = targetPath + File.separator + "menu.txt";
            this.customerFile = targetPath + File.separator + "customerList.txt";
        } catch (Exception e) {
            System.out.println("System Error initializing file paths: " + e.getMessage());
        }
    }

    private void loadMenuFromFile() {
        File file = new File(menuFile);
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("Food,F1001,Beef Burger,12.0,Main Course\n");
                writer.write("Food,F1002,Chicken Pizza,15.0,Main Course\n");
                writer.write("Drink,D2001,Coca Cola,2.0,Cold Drink\n");
            } catch (IOException e) {
                System.out.println("Database Error creating menu file: " + e.getMessage());
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] details = line.split(",");
                if (details.length < 5) {
                    continue;
                }

                String type = details[0].trim();
                String id = details[1].trim();
                String name = details[2].trim();
                double price = Double.parseDouble(details[3].trim());
                String extra = details[4].trim();

                if (type.equalsIgnoreCase("Food")) {
                    menu.add(new Food(id, name, price, extra));
                } else if (type.equalsIgnoreCase("Drink")) {
                    menu.add(new Drink(id, name, price, extra));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Database Error: Menu file not found. " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Database Error reading menu file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Database Error: Invalid price format in menu file. " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Database Error loading menu: " + e.getMessage());
        }
    }

    public void saveMenuToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(menuFile))) {
            for (MenuItem item : menu) {
                String type = item instanceof Food ? "Food" : "Drink";
                String extra = item.getItemCategory();
                writer.write(type + "," + item.getId() + "," + item.getName() + "," + item.getPrice() + "," + extra + "\n");
            }
        } catch (IOException e) {
            System.out.println("Database Error saving menu file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Database Error writing to menu: " + e.getMessage());
        }
    }

    private void loadCustomersFromFile() {
        File file = new File(customerFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Database Error creating customer file: " + e.getMessage());
            }
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] details = line.split(",");
                if (details.length < 6) {
                    continue;
                }

                int id = Integer.parseInt(details[0].trim());
                String name = details[1].trim();
                String phone = details[2].trim();
                String uuid = details[3].trim();
                int visits = Integer.parseInt(details[4].trim());
                int points = Integer.parseInt(details[5].trim());

                Customer customer = new Customer(id, name, phone, uuid);
                customer.setVisitCount(visits);
                customer.setLoyaltyPoints(points);
                customers.add(customer);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Database Error: Customer file not found. " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Database Error reading customer file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Database Error: Invalid customer format in file. " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Database Error loading customers: " + e.getMessage());
        }
    }

    public void saveCustomersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(customerFile))) {
            for (Customer customer : customers) {
                writer.write(customer.getId() + "," + customer.getName() + "," + customer.getPhone() + "," + customer.getCustomerUuid() + "," + customer.getVisitCount() + "," + customer.getLoyaltyPoints() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Database Error saving customer file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Database Error writing customer data: " + e.getMessage());
        }
    }

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
                    System.out.println("System Error generating product ID: " + e.getMessage());
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
            for (int i = 0; i < 3; i++) {
                sb.append(letters.charAt(r.nextInt(letters.length())));
            }
            for (int i = 0; i < 3; i++) {
                sb.append(r.nextInt(10));
            }
            String code = sb.toString();

            boolean exists = false;
            for (Customer c : customers) {
                if (c.getCustomerUuid().equals(code)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                return code;
            }
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
