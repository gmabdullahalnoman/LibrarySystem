## Phase: User Management System Upgrade
### Registration Workflow (Implemented|Tested)
 - Student registers → status = PENDING
 - Admin: Approve → ACTIVE | Deny → REJECTED
 - Add - status field in User.java
### Remove Direct Premium Registration (Implemented|Tested)
 - Comment out Premium from register
 - Add - Student → request upgrade | Admin → approve/reject
### User Control (Implemented|Tested)
 - Admin can - Update user (name, limit)|Delete user|Block user (no borrow)
 - Add - isBlocked ,borrowLimit (dynamic)
### Borrow Control Upgrade (Implemented|Tested)
 - Use borrowLimit instead of fixed constant
 - Admin can change limit anytime
### Role Upgrade Flow (Implemented|Tested)
 - Student → request Premium
 - Admin → approve → convert user type
### UI Enhancements (Implemented|Tested)
 - User menu - Show: Borrowed count | Limit | Status (Active/Blocked)

## Phase: Logic Fixing
### Premium Eligibility Rule
 - Condition - User must: Borrow ≥1; Return ≥1
 - Add in User.java - int totalBorrowed; int totalReturned
 - Update - borrowBook() → totalBorrowed++; returnBook() → totalReturned++
### Status-driven UI + Active Request
 - Problem - User stuck in PENDING → no visibility
 - Solution - In User.java Add: private boolean activationRequested;
 - Logic - If PENDING → show: “Request activation”
 - Add method - requestActivation()
### Admin: View All Users
 - Add in LibraryService - displayUsers(ArrayList<User> users)
### Prevent Deletion (BUSINESS RULE)
 - User delete rule - Cannot delete if user.getBorrowedCount() > 0
 - Book delete rules - Cannot delete if quantity > 0 ,or borrowed by any user
 - We need - In LibraryService boolean isBookBorrowed(Book book, ArrayList<User> users)
### Block + Status Messaging
 - Improve showBorrowedBooks() - 
 - Show - Status, Blocked, Pending actions: “Request activation”, “Premium pending”

 - Approve premium now shows pending premium requests first. 
 - Update/delete book now shows available books before input. 
 - Block/update user now shows user list first. 
 - Unblock user now shows only blocked users. 
 - Delete user now shows simplified user list (ID + name only). 
 - Fixed unblock case logic and replaced

### User Authentication System Implemented
 - Added Authentication Fields -  Inside User : username, password
 - Updated Constructors - All user types now require: name, username, password
 - Registration Upgrade - Implemented: username input, password input, duplicate username prevention
 - Login Upgrade - Old system: login by ID 
                 - New system: login by username + password
 - Authentication Validation - Implemented: 
                       incorrect password handling
                       username existence check
                       polite error messages
                       no program crash on invalid input
 - Admin User Visibility Upgrade - Admin can now view:
                                 usernames
                                 roles
                                 statuses
                                 limits
                                 blocked state
                                 Passwords remain hidden