package com.mycompany.oop_project;

import java.util.Scanner;

public class Cashier extends Person {

    private String username;
    private String password;

    public Cashier(int id, String name, String phone, String username, String password) {
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
            System.out.println("\n=== CASHIER DASHBOARD ===");
            System.out.println("1. New Order");
            System.out.println("2. Orders Status (Pending Orders)");
            System.out.println("3. View Menu");
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
                System.out.print("Enter Customer Phone Number (or 0 to return): ");
                String phone = sc.nextLine().trim();
                if (phone.equals("0")) {
                    continue;
                }

                Customer customer = null;
                for (Customer c : restaurant.getCustomers()) {
                    if (c.getPhone().equals(phone)) {
                        customer = c;
                        break;
                    }
                }

                if (customer != null) {
                    System.out.println("\nCustomer Found!");
                    System.out.println("ID: " + customer.getCustomerUuid() + " | Name: " + customer.getName() + " | Tier: " + customer.getLoyaltyTier());
                    System.out.print("Confirm Customer? (yes/no): ");
                    if (!sc.nextLine().equalsIgnoreCase("yes")) {
                        continue;
                    }
                } else {
                    System.out.print("Customer Not Found. Do you want to add them? (yes/no): ");
                    if (sc.nextLine().equalsIgnoreCase("yes")) {
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        String uuid = restaurant.generateUniqueCustomerUuid();
                        int nextId = restaurant.getCustomers().size() + 1;
                        customer = new Customer(nextId, name, phone, uuid);
                        restaurant.getCustomers().add(customer);
                        restaurant.saveCustomersToFile();
                        System.out.println("Customer Added with ID: " + uuid);
                    } else {
                        continue;
                    }
                }

                System.out.print("Do you want to start an order for " + customer.getName() + "? (yes/no): ");
                if (!sc.nextLine().equalsIgnoreCase("yes")) {
                    continue;
                }

                Order newOrder = new Order(restaurant.getOrders().size() + 1, customer);
                while (true) {
                    restaurant.printMenu();
                    System.out.print("Enter Product ID to add (or 0 to finish): ");
                    String pId = sc.nextLine().trim();
                    if (pId.equals("0")) {
                        break;
                    }

                    MenuItem item = null;
                    for (MenuItem m : restaurant.getMenu()) {
                        if (m.getId().equalsIgnoreCase(pId)) {
                            item = m;
                            break;
                        }
                    }

                    if (item != null) {
                        System.out.print("Quantity: ");
                        int qty;
                        try {
                            qty = Integer.parseInt(sc.nextLine().trim());
                        } catch (NumberFormatException e) {
                            System.out.println("Validation Error: Please enter a valid integer quantity.");
                            continue;
                        }

                        if (qty <= 0) {
                            System.out.println("Validation Error: Quantity must be greater than 0! Product not added.");
                            continue;
                        }

                        newOrder.addItem(item, qty);
                        System.out.println("Product added successfully.");
                    } else {
                        System.out.println("Invalid Product ID!");
                    }
                }

                if (newOrder.getItemsList().isEmpty()) {
                    System.out.println("Order cancelled because no items were added.");
                    continue;
                }

                double before = newOrder.calculateTotalBeforeDiscount();
                double after = newOrder.calculateFinalTotal();
                System.out.println("\n--- Invoice ---");
                System.out.println("Total Before Discount: " + before + " $");
                System.out.println("Final Total: " + after + " $");

                restaurant.getOrders().add(newOrder);
                restaurant.saveOrdersToFile();
                System.out.println("Order processed and set to PENDING status.");

            } else if (choice == 2) {
                boolean hasPending = false;
                for (Order o : restaurant.getOrders()) {
                    if (o.getOrderStatus().equals("pending")) {
                        hasPending = true;
                        break;
                    }
                }

                if (!hasPending) {
                    System.out.println("\nOops! No pending orders here...");
                    continue;
                }

                System.out.println(String.format("\n%-10s | %-12s | %-25s | %-12s | %-10s |", "OrderID", "CustomerID", "Customer Name", "Amount", "Status"));
                System.out.println("---------------------------------------------------------------------------------------------");
                for (Order o : restaurant.getOrders()) {
                    if (o.getOrderStatus().equals("pending")) {
                        System.out.println(String.format("%-10s | %-12s | %-25s | %-12.2f | %-10s |",
                                o.getFormattedOrderId(), o.getCustomer().getCustomerUuid(), o.getCustomer().getName(), o.calculateFinalTotal(), o.getOrderStatus()));
                    }
                }

                System.out.print("Enter Order ID to update (or 0 to return): ");
                String rawInput = sc.nextLine().trim();
                if (rawInput.equals("0") || rawInput.isEmpty()) {
                    continue;
                }

                int id;
                try {
                    id = Integer.parseInt(rawInput);
                } catch (NumberFormatException e) {
                    System.out.println("Validation Error: Please enter a valid order ID number.");
                    continue;
                }

                Order target = null;
                for (Order o : restaurant.getOrders()) {
                    if (o.getOrderId() == id && o.getOrderStatus().equals("pending")) {
                        target = o;
                        break;
                    }
                }

                if (target != null) {
                    System.out.println("1. Complete Order\n2. Cancel Order");
                    System.out.print("Select operation: ");

                    int op;
                    try {
                        op = Integer.parseInt(sc.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Validation Error: Please enter an integer option.");
                        continue;
                    }

                    if (op == 1) {
                        target.setOrderStatus("completed");
                        target.getCustomer().incrementVisits();
                        restaurant.saveCustomersToFile();
                        restaurant.saveOrdersToFile();
                        System.out.println("Order completed successfully.");
                    } else if (op == 2) {
                        target.setOrderStatus("canceled");
                        restaurant.saveOrdersToFile();
                        System.out.println("Order canceled.");
                    }
                } else {
                    System.out.println("Invalid Order ID or Order not pending.");
                }
            } else if (choice == 3) {
                restaurant.printMenu();
            }
        }
    }
}
