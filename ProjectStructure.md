## Functional Categorization (Approved as Present Structure)
src/main/java/org/example
│
├── Main.java
│
├── model / domain
│   ├── Person.java
│   ├── User.java
│   ├── AdminUser.java
│   ├── PremiumUser.java
│   ├── StudentUser.java
│   ├── Book.java
│   ├── Transaction.java
│   └── UserType.java
│
├── service
│   ├── AuthService.java
│   ├── UserService.java
│   └── LibraryService.java
│
├── contracts (interfaces)
│   ├── AuthOperations.java
│   ├── UserOperations.java
│   └── LibraryOperations.java
│
├── ui / menu
│   ├── AuthMenuHandler.java
│   ├── UserMenuHandler.java
│   └── AdminMenuHandler.java
│
├── infrastructure
│   └── DataStore.java
│
├── util
│   └── InputUtil.java
│
└── exception
├── AccessDeniedException.java
├── AuthenticationException.java
├── BookNotFoundException.java
├── InvalidInputException.java
└── InvalidOperationException.java

## Layer Wise
Presentation Layer
├── AuthMenuHandler
├── UserMenuHandler
└── AdminMenuHandler

Business Logic Layer
├── AuthService
├── UserService
└── LibraryService

Operation/Contract Layer
├── AuthOperations
├── UserOperations
└── LibraryOperations

Domain Layer
├── User
├── AdminUser
├── StudentUser
├── PremiumUser
├── Person
├── Book
├── Transaction
└── UserType

Persistence Layer
└── DataStore

Utility Layer
└── InputUtil

## Dependency Flow
Main
│
├── AuthMenuHandler
│      │
│      ▼
│   AuthService
│      │
│      ▼
│   DataStore
│
├── UserMenuHandler
│      │
│      ▼
│   UserService
│      │
│      ▼
│   DataStore
│
└── AdminMenuHandler
│
▼
LibraryService
│
▼
DataStore