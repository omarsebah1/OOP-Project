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

    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public void showDashboard(Restaurant restaurant, Scanner sc) {
        while (true) {
            System.out.println("\n=== CASHIER DASHBOARD ===");
            System.out.println("1. New Order");
            System.out.println("2. Orders Status (Pending Orders)");
            System.out.println("3. View Menu");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt(); sc.nextLine();

            if (choice == 4) break;

            if (choice == 1) {
                System.out.print("Enter Customer Phone Number (or 0 to return): ");
                String phone = sc.nextLine();
                if (phone.equals("0")) continue;

                Customer customer = null;
                for (Customer c : restaurant.getCustomers()) {
                    if (c.getPhone().equals(phone)) { customer = c; break; }
                }

                if (customer != null) {
                    System.out.println("\nCustomer Found!");
                    System.out.println("ID: " + customer.getCustomerUuid() + " | Name: " + customer.getName() + " | Tier: " + customer.getLoyaltyTier());
                    System.out.print("Confirm Customer? (yes/no): ");
                    if (!sc.nextLine().equalsIgnoreCase("yes")) continue;
                } else {
                    System.out.print("Customer Not Found. Do you want to add them? (yes/no): ");
                    if (sc.nextLine().equalsIgnoreCase("yes")) {
                        System.out.print("Enter Name: "); String name = sc.nextLine();
                        String uuid = restaurant.generateUniqueCustomerUuid();
                        customer = new Customer(restaurant.getCustomers().size() + 1, name, phone, uuid);
                        restaurant.getCustomers().add(customer);
                        System.out.println("Customer Added with ID: " + uuid);
                    } else continue;
                }

                System.out.print("Do you want to start an order for " + customer.getName() + "? (yes/no): ");
                if (!sc.nextLine().equalsIgnoreCase("yes")) continue;

                Order newOrder = new Order(restaurant.getOrders().size() + 1, customer);
                while (true) {
                    restaurant.printMenu();
                    System.out.print("Enter Product ID to add (or 0 to finish): ");
                    String pId = sc.nextLine();
                    if (pId.equals("0")) break;

                    MenuItem item = null;
                    for (MenuItem m : restaurant.getMenu()) {
                        if (m.getId().equalsIgnoreCase(pId)) { item = m; break; }
                    }

                    if (item != null) {
                        System.out.print("Quantity: "); int qty = sc.nextInt(); sc.nextLine();
                        newOrder.addItem(item, qty);
                    } else System.out.println("Invalid Product ID!");
                }

                if (newOrder.getItemsList().isEmpty()) continue;

                double before = newOrder.calculateTotalBeforeDiscount();
                double after = newOrder.calculateFinalTotal();
                System.out.println("\n--- Invoice ---");
                System.out.println("Total Before Discount: " + before + " $");
                System.out.println("Final Total: " + after + " $");
                
                restaurant.getOrders().add(newOrder);
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

                System.out.println(String.format("\n%-10s | %-12s | %-15s | %-12s | %-10s |", "OrderID", "CustomerID", "Customer Name", "Amount", "Status"));
                System.out.println("-----------------------------------------------------------------");
                for (Order o : restaurant.getOrders()) {
                    if (o.getOrderStatus().equals("pending")) {
                        System.out.println(String.format("%-10s | %-12s | %-15s | %-12.2f | %-10s |", 
                                o.getFormattedOrderId(), o.getCustomer().getCustomerUuid(), o.getCustomer().getName(), o.calculateFinalTotal(), o.getOrderStatus()));
                    }
                }
                
                System.out.print("Enter Order ID to update (or 0 to return): ");
                int id = sc.nextInt(); sc.nextLine();
                if (id == 0) continue;

                Order target = null;
                for (Order o : restaurant.getOrders()) {
                    if (o.getOrderId() == id && o.getOrderStatus().equals("pending")) { target = o; break; }
                }

                if (target != null) {
                    System.out.println("1. Complete Order\n2. Cancel Order");
                    System.out.print("Select operation: ");
                    int op = sc.nextInt(); sc.nextLine();
                    if (op == 1) {
                        target.setOrderStatus("completed");
                        int points = (int)(target.calculateFinalTotal() / 10);
                        target.getCustomer().incrementVisitsAndPoints(points);
                        System.out.println("Order completed successfully.");
                    } else if (op == 2) {
                        target.setOrderStatus("canceled");
                        System.out.println("Order canceled.");
                    }
                } else System.out.println("Invalid Order ID or Order not pending.");
            } else if (choice == 3) {
                restaurant.printMenu();
            }
        }
    }
}