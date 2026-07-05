package com.mycompany.oop_project;

public class Customer extends Person {
    private String customerUuid;
    private int visitCount;
    private int loyaltyPoints;

    public Customer(int id, String name, String phone, String customerUuid) {
        super(id, name, phone);
        this.customerUuid = customerUuid;
        this.visitCount = 0;
        this.loyaltyPoints = 0;
    }

    public String getCustomerUuid() { return customerUuid; }
    public int getVisitCount() { return visitCount; }
    public void incrementVisitsAndPoints(int points) {
        this.visitCount++;
        this.loyaltyPoints += points;
    }

    public String getLoyaltyTier() {
        if (visitCount >= 60) return "VIP";
        if (visitCount >= 40) return "Diamond";
        if (visitCount >= 30) return "Platinum";
        if (visitCount >= 20) return "Gold";
        if (visitCount >= 10) return "Silver";
        if (visitCount >= 5) return "Bronze";
        return "New Customer";
    }

    public double getDiscountPercentage(double orderTotal) {
        if (orderTotal <= 30.0 || visitCount < 5) return 0.0;
        String tier = getLoyaltyTier();
        switch (tier) {
            case "Bronze": return 0.02;
            case "Silver": return 0.03;
            case "Gold": return 0.05;
            case "Platinum": return 0.08;
            case "Diamond": return 0.11;
            case "VIP": return 0.15;
            default: return 0.0;
        }
    }
}