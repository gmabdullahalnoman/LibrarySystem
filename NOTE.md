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