package com.mycompany.oop_project;

public class Customer extends Person {

    private final String customerId;
    private int visitCount;

    public Customer(int id, String name, String phone, String customerId) {
        super(id, name, phone);
        this.customerId = customerId;
        this.visitCount = 0;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public void incrementVisits() {
        this.visitCount++;
    }

    public String getLoyaltyTier() {
        if (visitCount >= 60) {
            return "VIP";
        }
        if (visitCount >= 40) {
            return "Diamond";
        }
        if (visitCount >= 30) {
            return "Platinum";
        }
        if (visitCount >= 20) {
            return "Gold";
        }
        if (visitCount >= 10) {
            return "Silver";
        }
        if (visitCount >= 5) {
            return "Bronze";
        }
        return "New Customer";
    }

    public double getDiscountPercentage(double orderTotal) {
        if (orderTotal < 30.0 || this.visitCount < 5) {
            return 0.0;
        }

        String tier = getLoyaltyTier();
        return switch (tier) {
            case "Bronze" ->
                0.02;
            case "Silver" ->
                0.03;
            case "Gold" ->
                0.05;
            case "Platinum" ->
                0.08;
            case "Diamond" ->
                0.11;
            case "VIP" ->
                0.15;
            default ->
                0.0;
        };
    }
}
