# 🍽️ Restaurant Management System

A console-based **Restaurant Management System** built in **Java** using core **Object-Oriented Programming (OOP)** principles. The system supports two roles, **Admin** and **Cashier**, and manages the full restaurant workflow: menu items, customer records, orders, and a loyalty/discount program, with all data persisted to local text files.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [OOP Concepts Applied](#oop-concepts-applied)
- [Project Structure](#project-structure)
- [Class Diagram (Logical)](#class-diagram-logical)
- [Loyalty Tiers & Discounts](#loyalty-tiers--discounts)
- [Data Persistence](#data-persistence)
- [Default Login Credentials](#default-login-credentials)
- [How to Run](#how-to-run)
- [Authors](#authors)

---

<a id="overview"></a>
## 🔎 Overview

This project simulates a real restaurant's back-office system through a terminal interface. It was developed as an **OOP course project**, focusing on clean class design, inheritance hierarchies, abstraction, and file-based data storage (no external database required).

Two types of users can log in:
- **Admin** — manages the menu, monitors all orders, and views customer records.
- **Cashier** — registers customers, creates new orders, and updates order status.

---

<a id="features"></a>
## ✨ Features

### 👤 Admin Dashboard
- View, add, and remove menu products (Food or Drink)
- Filter and view orders by status: `pending`, `completed`, `canceled`, or all
- View the full customer list with visit counts and loyalty tiers

### 💳 Cashier Dashboard
- Search for an existing customer by phone number, or register a new one
- Create a new order and add multiple menu items with custom quantities
- View pending orders and mark them as `completed` or `canceled`
- Browse the current menu

### 🧾 Order System
- Auto-generated order IDs (formatted as 5-digit codes, e.g. `00001`)
- Automatic subtotal and final total calculation
- Loyalty-based discount applied automatically at checkout

### 🎁 Customer Loyalty Program
- Unique auto-generated customer ID (3 letters + 3 digits)
- Visit count tracked automatically when an order is completed
- Tier-based discount system (see below)

---

<a id="oop-concepts-applied"></a>
## 🧱 OOP Concepts Applied

| Concept | Where it's used |
|---|---|
| **Abstraction** | `Person` and `MenuItem` are abstract base classes defining shared contracts |
| **Inheritance** | `Admin`, `Cashier`, `Customer` → extend `Person`; `Food`, `Drink` → extend `MenuItem` |
| **Polymorphism** | `getItemDetails()`, `getItemType()`, `getItemCategory()` are overridden differently by `Food` and `Drink` |
| **Encapsulation** | All fields are `private`/`final` with controlled access via getters/setters |
| **Composition** | `Restaurant` is composed of `Admin`, `Cashier`, `Customer`, `MenuItem`, and `Order` objects; `Order` is composed of `OrderItem` objects |

---

<a id="project-structure"></a>
## 📁 Project Structure

```
src/main/java/com/mycompany/oop_project/
│
├── Main.java           # Entry point — handles login and routes to dashboards
├── Restaurant.java      # Core engine — manages menu, customers, orders & file I/O
│
├── Person.java          # Abstract base class (id, name, phone)
├── Admin.java           # extends Person — manages products, orders, customers
├── Cashier.java         # extends Person — handles order creation & status updates
├── Customer.java        # extends Person — loyalty tier & discount logic
│
├── MenuItem.java        # Abstract base class (id, name, price)
├── Food.java            # extends MenuItem — adds category (Main Course, Appetizers...)
├── Drink.java           # extends MenuItem — adds size (Small, Medium, Large)
│
├── Order.java           # Represents a customer order & total calculations
└── OrderItem.java       # Links a MenuItem with a quantity inside an order
```

---

<a id="class-diagram-logical"></a>
## 🗺️ Class Diagram (Logical)

```
Person (abstract)
 ├── Admin
 ├── Cashier
 └── Customer

MenuItem (abstract)
 ├── Food
 └── Drink

Restaurant
 ├── has-a Admin
 ├── has-a Cashier
 ├── has-a List<Customer>
 ├── has-a List<MenuItem>
 └── has-a List<Order>

Order
 └── has-a List<OrderItem>
      └── OrderItem has-a MenuItem + quantity
```

---

<a id="loyalty-tiers--discounts"></a>
## 🏆 Loyalty Tiers & Discounts

Tiers are based on the customer's total number of completed visits:

| Visits | Tier | Discount |
|---|---|---|
| 0 – 4 | New Customer | 0% |
| 5 – 9 | Bronze | 2% |
| 10 – 19 | Silver | 3% |
| 20 – 29 | Gold | 5% |
| 30 – 39 | Platinum | 8% |
| 40 – 59 | Diamond | 11% |
| 60+ | VIP | 15% |

> ⚠️ A discount is only applied if the order total is **≥ 30** *and* the customer has **at least 5 visits**.

---

<a id="data-persistence"></a>
## 💾 Data Persistence

The system stores all data locally as plain text files (no database needed), automatically created on first run under:

```
src/main/java/com/mycompany/oop_project/data/
├── menu.txt
├── customerList.txt
└── ordersList.txt
```

- `menu.txt` is auto-seeded with sample food & drink items if it doesn't exist.
- Customer and order data are loaded on startup and saved automatically after every change.

---

<a id="default-login-credentials"></a>
## 🔑 Default Login Credentials

| Role | Username | Password |
|---|---|---|
| Admin | `amir` | `amir` |
| Cashier | `omar` | `omar` |

---

<a id="how-to-run"></a>
## ▶️ How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/omarsebah1/OOP-Project.git
   ```
2. Open the project in **NetBeans** / **IntelliJ IDEA** / **Eclipse** (Maven project).
3. Run `Main.java` located at:
   ```
   src/main/java/com/mycompany/oop_project/Main.java
   ```
4. Log in with one of the credentials above and explore the dashboard.

---

<a id="authors"></a>
## 👨‍💻 Authors

**Amir Abualhin & Omar Abu Alsebah**
PTC2026 Java OOP Project
