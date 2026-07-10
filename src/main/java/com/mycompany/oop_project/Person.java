package com.mycompany.oop_project;

public abstract class Person {
    final private int id;
    final private String name;
    final private String phone;

    public Person(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
}