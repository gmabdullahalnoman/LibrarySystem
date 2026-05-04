## OOP concepts implemented in this version
### Classes and Objects
   System is built using Book, User, and Library classes
   Objects represent real-world entities and interact with each other 
### Encapsulation
   All fields are private
   Access controlled via methods (getters and behavior methods)
### Object Interaction
   Library coordinates between Book and User
   User maintains list of borrowed books
   Book maintains availability state

## Structural improvements
 - Removed previous design
 - Introduced LibraryService as logic layer
 - Added interface-based abstraction
### OOP improvements
 - Implemented Abstraction via interface
 - Strong Classes & Objects
 - Clean Encapsulation
## Did:
 - Created Person with common fields: id, name
 - Updated User → now inherits from Person
 - Removed duplication of user identity fields
### OOP Improvement
 - Implemented Inheritance (IS-A relationship)
 - Improved code reuse 
 - Prepared structure for future classes (Admin, Member)
## Did:
 - Created StudentUser and PremiumUser
 - Overrode borrowBook() with limits
 - Added getBorrowedCount() in User
 - Updated Main to use child class object
### OOP Concepts Implemented
 - Polymorphism (Runtime) - Same method → different behavior based on object
 - Method Overriding - Child classes redefine parent behavior
 - Dynamic Binding - Method decided at runtime (JVM)
## Did:
 - Replaced hardcoded flow with interactive menu
 - Added Scanner for user input
 - Introduced loop + switch-case for continuous operation
### OOP / Design Improvements
 - Real Execution Flow - Program now behaves like a real system, not a demo script
 - Separation still maintained - Main handles input, LibraryService handles logic
 - Extensibility - Easy to plug in new features (user types, validation, etc.)
## Did
 - Allowed user type selection at runtime
 - Removed fixed default user
### OOP Improvements
 - Inheritance Expanded - Created hierarchy:Person → User → (StudentUsfer, PremiumUser, AdminUser)
 - Polymorphism Strengthened - Same User reference → different behavior per type
 - Role-Based Design - Student → limited books ;Premium → higher limit ;Admin → no limit
## Did
 - Checked empty book title/author ,
 - Handled invalid menu choices
 - Prevented incorrect data entry
 - Exception Handling 
 - Wrapped main flow in try-catch 
 - Prevented system crash on unexpected input 
 - Handled invalid scanner input safely 
 - Resource Management 
 - Closed Scanner properly using finally
### Final Implementation (As Planned)
 - Reliability - Program no longer breaks on bad input 
 - Robust Design - System can handle real user behavior 
 - Professional Practice - Proper use of try-catch-finally

## Improved so far:

### Encapsulation & Static
 - Data Integrity - Book ID
 - Auto-increment ID (static counter)
 - Prevent duplicate IDs
### Business Rule Enforcement
 - Correct Business Logic (Borrow Flow)
 - Fixed wrong order (user limit before marking book borrowed)
### Method overriding (Runtime polymorphism)
 - Polymorphism (User Types)
 - Student / Premium / Admin wise limits
### Collection (ArrayList); Separation of concerns ;Basic authentication flow
 - System Design Upgrade (Multi-user)
 - Added: Registration, Login by ID, Multiple users in memory

## Next Target:
### Role-Based Access Control (RBAC)
 - Only Admin can add books 
 - Enforced at service layer
      - Polymorphism → instanceof AdminUser
      - Abstraction → interface updated
      - Encapsulation → control access via service
      - Separation of Concerns → UI ≠ Business logic
### User-specific Functionalities
### Book Stock System
