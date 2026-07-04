package com.mycompany.oop_project;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();
        
        Cashier defaultCashier = new Cashier(1, "John Doe", "0599000000", "CASH-01");
        restaurant.addCashier(defaultCashier);

        restaurant.addMenuItem(new Food(101, "Beef Burger", 25.0, "Main Course"));
        restaurant.addMenuItem(new Food(102, "Chicken Pizza", 40.0, "Main Course"));
        restaurant.addMenuItem(new Food(103, "French Fries", 12.0, "Appetizer"));
        restaurant.addMenuItem(new Drink(201, "Coca Cola", 5.0, "Medium", "Cold"));
        restaurant.addMenuItem(new Drink(202, "Hot Tea", 4.0, "Small", "Hot"));

        Scanner scanner = new Scanner(System.in);
        int choice1 = 0;

        while (choice1 != 4) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Orders");
            System.out.println("2. Customers");
            System.out.println("3. Products");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice1 = scanner.nextInt();
            scanner.nextLine();

            if (choice1 == 1) {
                System.out.println("\n--- ORDERS MENU ---");
                System.out.println("1. New Order");
                System.out.println("2. In Progress Orders");
                System.out.println("3. Completed Orders");
                System.out.println("4. Canceled Orders");
                System.out.print("Enter your choice: ");
                int choice2 = scanner.nextInt();
                scanner.nextLine();

                if (choice2 == 1) {
                    System.out.print("Enter Customer Phone: ");
                    String phone = scanner.nextLine();
                    Customer customer = restaurant.findCustomerByPhone(phone);

                    if (customer != null) {
                        System.out.println("\nCustomer Found!");
                        System.out.println("Name: " + customer.getName());
                        System.out.println("Phone: " + customer.getPhone());
                        System.out.println("Visits: " + customer.getVisitCount());
                        System.out.println("Loyalty Points: " + customer.getLoyaltyPoints());
                        System.out.println("Tier: " + customer.getLoyaltyTier());
                        System.out.println("Discount Available: " + (customer.getDiscountPercentage() * 100) + "%");
                        
                        System.out.print("Confirm Customer? (yes/no): ");
                        String confirm = scanner.nextLine();
                        if (!confirm.equalsIgnoreCase("yes")) {
                            continue;
                        }
                    } else {
                        System.out.println("Customer not found. Would you like to add a new customer?");
                        System.out.print("(yes/no): ");
                        String addConfirm = scanner.nextLine();
                        if (addConfirm.equalsIgnoreCase("yes")) {
                            System.out.print("Enter Customer ID (Number): ");
                            int newId = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Enter Customer Name: ");
                            String newName = scanner.nextLine();
                            
                            customer = new Customer(newId, newName, phone);
                            restaurant.addCustomer(customer);
                            System.out.println("Customer saved successfully!");
                            
                            System.out.print("Do you want to create an order for " + customer.getName() + "? (yes/no): ");
                            String orderConfirm = scanner.nextLine();
                            if (!orderConfirm.equalsIgnoreCase("yes")) {
                                continue;
                            }
                        } else {
                            continue;
                        }
                    }

                    int orderId = restaurant.getOrders().size() + 1;
                    Order newOrder = new Order(orderId, defaultCashier, customer);

                    boolean addingItems = true;
                    while (addingItems) {
                        System.out.println("\n--- Menu Items ---");
                        for (MenuItem item : restaurant.getMenu()) {
                            System.out.println(item.getId() + ". " + item.getItemDetails());
                        }
                        System.out.print("Enter Product ID to add (or 0 to finish): ");
                        int prodId = scanner.nextInt();
                        if (prodId == 0) {
                            addingItems = false;
                            break;
                        }

                        MenuItem selectedItem = null;
                        for (MenuItem item : restaurant.getMenu()) {
                            if (item.getId() == prodId) {
                                selectedItem = item;
                                break;
                            }
                        }

                        if (selectedItem != null) {
                            System.out.print("Enter Quantity: ");
                            int qty = scanner.nextInt();
                            newOrder.addItem(selectedItem, qty);
                            System.out.println("Item added to order.");
                        } else {
                            System.out.println("Invalid Product ID!");
                        }
                    }

                    if (newOrder.getItemsList().isEmpty()) {
                        System.out.println("Order is empty. Canceled.");
                        continue;
                    }

                    System.out.println("\nTotal Before Discount: " + newOrder.calculateTotalBeforeDiscount() + " ILS");
                    System.out.println("Final Total After Discount: " + newOrder.calculateFinalTotal() + " ILS");
                    System.out.print("Select Payment Method (Cash/Bank): ");
                    scanner.nextLine();
                    String payMethod = scanner.nextLine();

                    newOrder.finalizeOrder(payMethod);
                    restaurant.addOrder(newOrder);
                    System.out.println("Order #" + orderId + " completed and paid successfully!");

                } else if (choice2 == 2) {
                    ArrayList<Order> list = restaurant.getOrdersByStatus("In Progress");
                    System.out.println("\n--- In Progress Orders ---");
                    for (Order o : list) {
                        System.out.println("Order ID: " + o.getOrderId() + " | Customer: " + o.getCustomer().getName() + " | Total: " + o.calculateFinalTotal());
                    }
                } else if (choice2 == 3) {
                    ArrayList<Order> list = restaurant.getOrdersByStatus("Completed");
                    System.out.println("\n--- Completed Orders ---");
                    for (Order o : list) {
                        System.out.println("Order ID: " + o.getOrderId() + " | Customer: " + o.getCustomer().getName() + " | Total: " + o.calculateFinalTotal() + " | Method: " + o.getPaymentMethod());
                    }
                } else if (choice2 == 4) {
                    ArrayList<Order> list = restaurant.getOrdersByStatus("Canceled");
                    System.out.println("\n--- Canceled Orders ---");
                    for (Order o : list) {
                        System.out.println("Order ID: " + o.getOrderId() + " | Customer: " + o.getCustomer().getName());
                    }
                }

            } else if (choice1 == 2) {
                System.out.println("\n--- CUSTOMERS LIST ---");
                for (Customer c : restaurant.getCustomers()) {
                    System.out.println("ID: " + c.getId() + " | Name: " + c.getName() + " | Phone: " + c.getPhone() + " | Tier: " + c.getLoyaltyTier() + " | Visits: " + c.getVisitCount());
                }
            } else if (choice1 == 3) {
                System.out.println("\n--- PRODUCTS MENU ---");
                for (MenuItem item : restaurant.getMenu()) {
                    System.out.println(item.getItemDetails());
                }
            }
        }
        System.out.println("Exiting System. Goodbye!");
    }
}