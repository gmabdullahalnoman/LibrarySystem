## Phase: User Management System Upgrade
### Registration Workflow
 - Student registers → status = PENDING
 - Admin: Approve → ACTIVE | Deny → REJECTED
 - Add - status field in User.java
### Remove Direct Premium Registration
 - Comment out Premium from register
 - Add - Student → request upgrade | Admin → approve/reject
### User Control (Admin)
 - Admin can - Update user (name, limit)|Delete user|Block user (no borrow)
 - Add - isBlocked ,borrowLimit (dynamic)
### Borrow Control Upgrade
 - Use borrowLimit instead of fixed constant
 - Admin can change limit anytime
### Role Upgrade Flow
 - Student → request Premium
 - Admin → approve → convert user type
### UI Enhancements
 - User menu - Show: Borrowed count | Limit | Status (Active/Blocked)