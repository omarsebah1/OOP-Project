package com.mycompany.oop_project;

import java.util.Scanner;

public class Admin extends Person {

    private String username;
    private String password;

    public Admin(int id, String name, String phone, String username, String password) {
        super(id, name, phone);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void showDashboard(Restaurant restaurant, Scanner sc) {
        while (true) {
            System.out.println("\n=== ADMIN DASHBOARD ===");
            System.out.println("1. Manage Products");
            System.out.println("2. Manage Orders");
            System.out.println("3. View Customers");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Validation Error: Please enter a valid integer number.");
                continue;
            }

            if (choice == 4) {
                break;
            }

            if (choice == 1) {
                manageProducts(restaurant, sc);
            } else if (choice == 2) {
                manageOrders(restaurant, sc);
            } else if (choice == 3) {
                viewCustomers(restaurant);
            }
        }
    }

    private void manageProducts(Restaurant restaurant, Scanner sc) {
        while (true) {
            System.out.println("\n--- MANAGE PRODUCTS ---");
            System.out.println("1. Show All Products");
            System.out.println("2. Add New Product");
            System.out.println("3. Remove Product");
            System.out.println("4. Back");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Validation Error: Please enter a valid integer number.");
                continue;
            }

            if (choice == 4) {
                break;
            }

            if (choice == 1) {
                restaurant.printMenu();
            } else if (choice == 2) {
                System.out.println("\nSelect Product Type:");
                System.out.println("1. Food");
                System.out.println("2. Drink");
                System.out.print("Enter your selection (1-2) or 0 to cancel: ");

                String typeChoice = sc.nextLine().trim();
                if (typeChoice.equals("0")) {
                    System.out.println("Operation cancelled.");
                    continue;
                }

                String typePrefix;
                if (typeChoice.equals("1")) {
                    typePrefix = "F";
                } else if (typeChoice.equals("2")) {
                    typePrefix = "D";
                } else {
                    System.out.println("Invalid selection!");
                    continue;
                }

                System.out.print("Enter Name (or 0 to cancel): ");
                String name = sc.nextLine().trim();
                if (name.equals("0") || name.isEmpty()) {
                    System.out.println("Operation cancelled.");
                    continue;
                }

                System.out.print("Enter Price (or 0 to cancel): ");
                String priceInput = sc.nextLine().trim();
                if (priceInput.equals("0")) {
                    System.out.println("Operation cancelled.");
                    continue;
                }

                double price;
                try {
                    price = Double.parseDouble(priceInput);
                    if (price < 0) {
                        System.out.println("Validation Error: Price cannot be negative!");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Validation Error: Please enter a valid price value.");
                    continue;
                }

                String extraInfo = "";
                if (typePrefix.equals("F")) {
                    System.out.println("\nSelect Category:");
                    System.out.println("1. Main Course");
                    System.out.println("2. Appetizers");
                    System.out.println("3. Desserts");
                    System.out.print("Enter your selection (1-3) or 0 to cancel: ");

                    String catChoice = sc.nextLine().trim();
                    if (catChoice.equals("0")) {
                        System.out.println("Operation cancelled.");
                        continue;
                    }

                    if (catChoice.equals("1")) {
                        extraInfo = "Main Course";
                    } else if (catChoice.equals("2")) {
                        extraInfo = "Appetizers";
                    } else if (catChoice.equals("3")) {
                        extraInfo = "Desserts";
                    } else {
                        System.out.println("Invalid selection! Operation cancelled.");
                        continue;
                    }
                } else {
                    System.out.println("\nSelect Size:");
                    System.out.println("1. Small");
                    System.out.println("2. Medium");
                    System.out.println("3. Large");
                    System.out.print("Enter your selection (1-3) or 0 to cancel: ");

                    String sizeChoice = sc.nextLine().trim();
                    if (sizeChoice.equals("0")) {
                        System.out.println("Operation cancelled.");
                        continue;
                    }

                    if (sizeChoice.equals("1")) {
                        extraInfo = "Small Size";
                    } else if (sizeChoice.equals("2")) {
                        extraInfo = "Medium Size";
                    } else if (sizeChoice.equals("3")) {
                        extraInfo = "Large Size";
                    } else {
                        System.out.println("Invalid selection! Operation cancelled.");
                        continue;
                    }
                }

                String generatedId = restaurant.generateNextProductId(typePrefix);
                if (typePrefix.equals("F")) {
                    restaurant.getMenu().add(new Food(generatedId, name, price, extraInfo));
                } else {
                    restaurant.getMenu().add(new Drink(generatedId, name, price, extraInfo));
                }

                restaurant.saveMenuToFile();
                System.out.println("Item added successfully with ID: " + generatedId);

            } else if (choice == 3) {
                restaurant.printMenu();
                System.out.print("Enter Product ID to remove (or 0 to cancel): ");
                String pId = sc.nextLine().trim();
                if (pId.equals("0") || pId.isEmpty()) {
                    continue;
                }

                MenuItem target = null;
                for (MenuItem m : restaurant.getMenu()) {
                    if (m.getId().equalsIgnoreCase(pId)) {
                        target = m;
                        break;
                    }
                }

                if (target != null) {
                    restaurant.getMenu().remove(target);
                    restaurant.saveMenuToFile();
                    System.out.println("Product removed successfully.");
                } else {
                    System.out.println("Product ID not found!");
                }
            }
        }
    }

    private void manageOrders(Restaurant restaurant, Scanner sc) {
        while (true) {
            System.out.println("\n--- MANAGE ORDERS ---");
            System.out.println("1. Completed Orders");
            System.out.println("2. Pending Orders");
            System.out.println("3. Canceled Orders");
            System.out.println("4. Show All Orders");
            System.out.println("5. Back");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Validation Error: Please enter a valid integer number.");
                continue;
            }

            if (choice == 5) {
                break;
            }

            String filterStatus = "";
            if (choice == 1) {
                filterStatus = "completed";
            } else if (choice == 2) {
                filterStatus = "pending";
            } else if (choice == 3) {
                filterStatus = "canceled";
            }

            System.out.println(String.format("\n%-10s | %-12s | %-25s | %-12s | %-10s |", "OrderID", "CustomerID", "Customer Name", "Amount", "Status"));
            System.out.println("---------------------------------------------------------------------------------------------");

            for (Order o : restaurant.getOrders()) {
                if (filterStatus.isEmpty() || o.getOrderStatus().equals(filterStatus)) {
                    System.out.println(String.format("%-10s | %-12s | %-25s | %-12.2f | %-10s |",
                            o.getFormattedOrderId(), o.getCustomer().getCustomerUuid(), o.getCustomer().getName(), o.calculateFinalTotal(), o.getOrderStatus()));
                }
            }
        }
    }

    private void viewCustomers(Restaurant restaurant) {
        System.out.println(String.format("\n%-5s | %-25s | %-15s | %-10s | %-12s | %-12s |", "ID", "Name", "Phone", "UUID", "Visits", "Loyalty Tier"));
        System.out.println("---------------------------------------------------------------------------------------------");
        for (Customer c : restaurant.getCustomers()) {
            System.out.println(String.format("%-5d | %-25s | %-15s | %-10s | %-12d | %-12s |",
                    c.getId(), c.getName(), c.getPhone(), c.getCustomerUuid(), c.getVisitCount(), c.getLoyaltyTier()));
        }
    }
}
