package com.mycompany.oop_project;

import java.util.Scanner;

public class Admin {
    private String username;
    private String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public void showDashboard(Restaurant restaurant, Scanner sc) {
        while (true) {
            System.out.println("\n=== ADMIN DASHBOARD ===");
            System.out.println("1. Manage Orders");
            System.out.println("2. Manage Products");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt(); sc.nextLine();

            if (choice == 3) break;

            if (choice == 1) {
                while (true) {
                    System.out.println("\n--- MANAGE ORDERS ---");
                    System.out.println("1. Completed Orders");
                    System.out.println("2. Pending Orders");
                    System.out.println("3. Canceled Orders");
                    System.out.println("4. Show All Orders");
                    System.out.println("5. Back");
                    System.out.print("Enter your choice: ");
                    int subChoice = sc.nextInt(); sc.nextLine();

                    if (subChoice == 5) break;
                    if (subChoice < 1 || subChoice > 4) {
                        System.out.println("Invalid choice!");
                        continue;
                    }

                    if (restaurant.getOrders().isEmpty()) {
                        System.out.println("\nThere are no orders in the system yet.");
                        continue;
                    }

                    String filterStatus = "";
                    boolean showAll = false;
                    if (subChoice == 1) filterStatus = "completed";
                    else if (subChoice == 2) filterStatus = "pending";
                    else if (subChoice == 3) filterStatus = "canceled";
                    else if (subChoice == 4) showAll = true;

                    boolean hasOrders = false;
                    for (Order o : restaurant.getOrders()) {
                        if (showAll || o.getOrderStatus().equalsIgnoreCase(filterStatus)) {
                            hasOrders = true;
                            break;
                        }
                    }

                    if (!hasOrders) {
                        System.out.println("\nOops! No orders found in this section.");
                        continue;
                    }

                    System.out.println(String.format("\n%-10s | %-12s | %-15s | %-12s | %-10s |", "OrderID", "CustomerID", "Customer Name", "Amount", "Status"));
                    System.out.println("-----------------------------------------------------------------");
                    for (Order o : restaurant.getOrders()) {
                        if (showAll || o.getOrderStatus().equalsIgnoreCase(filterStatus)) {
                            System.out.println(String.format("%-10s | %-12s | %-15s | %-12.2f | %-10s |", 
                                    o.getFormattedOrderId(), o.getCustomer().getCustomerUuid(), o.getCustomer().getName(), o.calculateFinalTotal(), o.getOrderStatus()));
                        }
                    }
                }
            } else if (choice == 2) {
                while (true) {
                    System.out.println("\n--- MANAGE PRODUCTS ---");
                    System.out.println("1. Show All Products");
                    System.out.println("2. Add New Product");
                    System.out.println("3. Remove Product");
                    System.out.println("4. Back");
                    System.out.print("Enter your choice: ");
                    int subChoice = sc.nextInt(); sc.nextLine();

                    if (subChoice == 4) break;

                    if (subChoice == 1) {
                        restaurant.printMenu();
                    } else if (subChoice == 2) {
                        System.out.println("\nSelect Product Type:");
                        System.out.println("1. Food");
                        System.out.println("2. Drink");
                        System.out.print("Enter your selection (1-2): ");
                        int type = sc.nextInt(); sc.nextLine();
                        
                        if (type != 1 && type != 2) {
                            System.out.println("Invalid selection!");
                            continue;
                        }

                        System.out.print("Enter Name: "); String name = sc.nextLine();
                        System.out.print("Enter Price: "); double price = sc.nextDouble(); sc.nextLine();
                        
                        if (type == 1) {
                            System.out.println("\nSelect Category:");
                            System.out.println("1. Main Course");
                            System.out.println("2. Appetizers");
                            System.out.println("3. Desserts");
                            System.out.print("Enter your selection (1-3): ");
                            int catChoice = sc.nextInt(); sc.nextLine();
                            String cat = "General Food";
                            if (catChoice == 1) cat = "Main Course";
                            else if (catChoice == 2) cat = "Appetizers";
                            else if (catChoice == 3) cat = "Desserts";
                            
                            String id = restaurant.generateNextProductId("F");
                            restaurant.getMenu().add(new Food(id, name, price, cat));
                        } else {
                            System.out.println("\nSelect Size:");
                            System.out.println("1. Small");
                            System.out.println("2. Medium");
                            System.out.println("3. Large");
                            System.out.print("Enter your selection (1-3): ");
                            int sizeChoice = sc.nextInt(); sc.nextLine();
                            String size = "Medium";
                            if (sizeChoice == 1) size = "Small";
                            else if (sizeChoice == 2) size = "Medium";
                            else if (sizeChoice == 3) size = "Large";
                            
                            String id = restaurant.generateNextProductId("D");
                            restaurant.getMenu().add(new Drink(id, name, price, size));
                        }
                        System.out.println("Item added successfully!");
                    } else if (subChoice == 3) {
                        System.out.print("Enter Product ID to remove: ");
                        String id = sc.nextLine();
                        MenuItem toRemove = null;
                        for (MenuItem m : restaurant.getMenu()) {
                            if (m.getId().equalsIgnoreCase(id)) { toRemove = m; break; }
                        }
                        if (toRemove != null) {
                            restaurant.getMenu().remove(toRemove);
                            System.out.println("Item removed successfully!");
                        } else {
                            System.out.println("Product ID not found!");
                        }
                    }
                }
            }
        }
    }
}