# Library Management System (Java OOP Project)
## Overview

This is a simple Library Management System built using Java to practice and gradually implement 
all core Object-Oriented Programming (OOP) concepts.
The project models real-world entities like books, users, and a library system where borrowing
and returning books is handled.

## Classes and Objects

Book → Represents a book entity with properties like id, title, author, and availability status
User → Represents a library user who can borrow and return books
Library → Manages the collection of books and handles operations such as adding, borrowing,
and returning.

## OOP Concepts Implemented (Current Stage)
### Classes and Objects
The system is structured using multiple classes representing real-world entities
Objects are created from these classes to perform operations
### Encapsulation
All data members are declared as private
Access to data is controlled through public methods
Ensures data security and controlled modification

## Plan (OOP Roadmap)

This project will be extended step by step to implement all OOP pillars:

Step 1: Classes and Objects (Completed)
Step 2: Encapsulation (Completed)
Step 3: Inheritance (Pending)
Step 4: Polymorphism (Pending)
Step 5: Abstraction (Pending)

Each concept will be introduced InshaAllah.

## Summary

The main goal of this project is not only to implement functionality but to build a strong 
understanding of all OOP principles through structured development.

## Project Summary
Developed a Java-based Library Management System step by step, evolving it from a simple console
program into a structured OOP application.
### OOP Concepts Implemented
 - Encapsulation - Private fields in Book and User ,Controlled access via getters and methods
 - Abstraction - LibraryOperations interface defines system behavior ,LibraryService hides internal logic
 - Inheritance ,Person → base class ,User extends Person Extended into StudentUser, PremiumUser, AdminUser
 - Polymorphism - Method overriding (borrowBook), Runtime behavior changes based on user type 
### System Features Built
- Library Functions - Add books ,Display books ,Borrow books ,Return books 
- User System - Multiple user roles: Student (limited borrowing), Premium (higher limit), Admin (no limit)
- Tracks borrowed books per user
### Runtime System
 - Console-based menu system 
 - Scanner-based user input 
 - Continuous interaction loop
### Reliability Improvements
 - Input validation 
 - Exception handling (try-catch)
 - Safe scanner usage 
 - Empty input checks

### Next Upgrades (Planning Stage)
 - Database Integration
 - Spring Boot backend system
 - REST API version