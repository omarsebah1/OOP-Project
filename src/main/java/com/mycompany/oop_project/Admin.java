package com.mycompany.oop_project;

import java.util.Scanner;

public class Admin extends Person {

    private final String username;
    private final String password;

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

            switch (choice) {
                case 1 ->
                    manageProducts(restaurant, sc);
                case 2 ->
                    manageOrders(restaurant, sc);
                case 3 ->
                    viewCustomers(restaurant);
                case 4 -> {
                    break;
                }
                default ->
                    System.out.println("Invalid Option! Please select a number between 1 and 4.");
            }

            if (choice == 4) {
                break;
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

            switch (choice) {
                case 1 ->
                    restaurant.printMenu();
                case 2 -> {
                    System.out.println("\nSelect Product Type:");
                    System.out.println("1. Food");
                    System.out.println("2. Drink");
                    System.out.print("Enter your selection (1-2) or 0 to cancel: ");

                    String typeChoice = sc.nextLine().trim();
                    if (typeChoice.equals("0")) {
                        System.out.println("Operation cancelled.");
                        continue;
                    }

                    String typePrefix = switch (typeChoice) {
                        case "1" ->
                            "F";
                        case "2" ->
                            "D";
                        default -> {
                            System.out.println("Invalid selection!");
                            yield null;
                        }
                    };

                    if (typePrefix == null) {
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

                        extraInfo = switch (catChoice) {
                            case "1" ->
                                "Main Course";
                            case "2" ->
                                "Appetizers";
                            case "3" ->
                                "Desserts";
                            default -> {
                                System.out.println("Invalid selection! Operation cancelled.");
                                yield null;
                            }
                        };

                        if (extraInfo == null) {
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

                        extraInfo = switch (sizeChoice) {
                            case "1" ->
                                "Small Size";
                            case "2" ->
                                "Medium Size";
                            case "3" ->
                                "Large Size";
                            default -> {
                                System.out.println("Invalid selection! Operation cancelled.");
                                yield null;
                            }
                        };

                        if (extraInfo == null) {
                            continue;
                        }
                    }

                    String generatedId = restaurant.generateProductId(typePrefix);
                    if (typePrefix.equals("F")) {
                        restaurant.getMenu().add(new Food(generatedId, name, price, extraInfo));
                    } else {
                        restaurant.getMenu().add(new Drink(generatedId, name, price, extraInfo));
                    }

                    restaurant.saveMenu();
                    System.out.println("Item added successfully with ID: " + generatedId);
                }
                case 3 -> {
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
                        restaurant.saveMenu();
                        System.out.println("Product removed successfully.");
                    } else {
                        System.out.println("Product ID not found!");
                    }
                }
                default ->
                    System.out.println("Invalid Option! Please select a number between 1 and 4.");
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

            String filterStatus = switch (choice) {
                case 1 ->
                    "completed";
                case 2 ->
                    "pending";
                case 3 ->
                    "canceled";
                case 4 ->
                    "";
                default -> {
                    System.out.println("Invalid Option! Setting filter to Show All.");
                    yield "";
                }
            };

            System.out.println(String.format("\n%-10s | %-12s | %-25s | %-12s | %-10s |", "OrderID", "CustomerID", "Customer Name", "Amount", "Status"));
            System.out.println("---------------------------------------------------------------------------------------------");

            for (Order o : restaurant.getOrders()) {
                if (filterStatus.isEmpty() || o.getOrderStatus().equals(filterStatus)) {
                    System.out.println(String.format("%-10s | %-12s | %-25s | %-12.2f | %-10s |",
                            o.getFormattedOrderId(), o.getCustomer().getCustomerId(), o.getCustomer().getName(), o.calculateFinalTotal(), o.getOrderStatus()));
                }
            }
        }
    }

    private void viewCustomers(Restaurant restaurant) {
        System.out.println(String.format("\n%-5s | %-25s | %-15s | %-10s | %-12s | %-12s |", "ID", "Name", "Phone", "UUID", "Visits", "Loyalty Tier"));
        System.out.println("---------------------------------------------------------------------------------------------");
        for (Customer c : restaurant.getCustomers()) {
            System.out.println(String.format("%-5d | %-25s | %-15s | %-10s | %-12d | %-12s |",
                    c.getId(), c.getName(), c.getPhone(), c.getCustomerId(), c.getVisitCount(), c.getLoyaltyTier()));
        }
    }
}
