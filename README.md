# BookShop - Spring Boot Application

A full-featured online bookstore application built with Spring Boot, MySQL, and Thymeleaf. This project demonstrates a complete e-commerce solution with separate admin and customer functionalities.

## 📋 Features

### Admin Features
- **Secure Login**: Pre-configured admin account with session management
- **Book Management**: Complete CRUD operations for book inventory
  - Add new books with title, author, year, price, and stock quantity
  - Edit existing book details
  - Delete books from inventory
- **Success Notifications**: Clear feedback for all admin actions

### Customer Features
- **User Registration**: Complete signup with personal details
  - Name, surname, date of birth
  - Address, phone number, email
- **Authentication**: Secure login/logout functionality
- **Book Browsing**: View all available books without login requirement
- **Shopping Cart**: 
  - Add books to cart (login required)
  - View cart with item details and total price
  - Remove items from cart
  - Cart persists in database
- **Checkout Process**: 
  - Order summary display
  - Mock payment processing (no real payment integration)
  - Inventory automatically updates after purchase
  - No order history stored (as per requirements)

## 🛠️ Technologies Used

- **Backend**: Spring Boot 3.1.0
- **Database**: MySQL 8.0
- **Template Engine**: Thymeleaf
- **Build Tool**: Maven
- **Frontend**: HTML, CSS (Custom styling)
- **Java Version**: 17

## 📦 Prerequisites

Before running this application, ensure you have:

- Java JDK 17 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher
- Git (for cloning the repository)

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/azizosharke/bookshop-spring-boot.git
cd bookshop-spring-boot
```

### 2. MySQL Database Setup
Create a database for the application:
```sql
CREATE DATABASE bookshop;
```

### 3. Configure Application Properties
Update `src/main/resources/application.properties` with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookshop?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### 4. Build and Run
```bash
# Install dependencies and build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

### 5. Access the Application
Open your browser and navigate to:
```
http://localhost:8080
```

## 👤 Default Credentials

### Admin Account
- **Username**: `admin`
- **Password**: `admin123`

## 📁 Project Structure

```
bookshop-spring-boot/
├── src/main/java/com/bookshop/
│   ├── BookshopApplication.java       # Main application class
│   ├── config/
│   │   └── DataInitializer.java       # Initial data setup
│   ├── controller/
│   │   ├── AdminController.java       # Admin operations
│   │   ├── AuthController.java        # Login/Register
│   │   ├── CustomerController.java    # Customer operations
│   │   └── HomeController.java        # Home page
│   ├── model/
│   │   ├── Book.java                  # Book entity
│   │   ├── Cart.java                  # Cart entity
│   │   ├── CartItem.java              # Cart items
│   │   ├── User.java                  # User entity
│   │   └── UserRole.java              # User roles enum
│   ├── repository/                    # JPA repositories
│   └── service/                       # Business logic
├── src/main/resources/
│   ├── application.properties         # Configuration
│   ├── static/css/                    # CSS files
│   └── templates/                     # Thymeleaf templates
│       ├── admin/                     # Admin views
│       └── customer/                  # Customer views
└── pom.xml                           # Maven configuration
```

## 🎯 Key Features Explained

### Session Management
- Session-based authentication without Spring Security
- Role-based access control (Admin vs Customer)
- Automatic session handling for cart persistence

### Inventory Management
- Real-time stock updates when orders are placed
- Prevents overselling with stock validation
- Admin can monitor and update inventory

### Shopping Cart
- Database-persisted cart for registered users
- Automatic cart creation on first item add
- Cart cleared after successful checkout

## 📝 Database Schema

The application automatically creates the following tables:
- `users` - Stores admin and customer information
- `books` - Product catalog
- `carts` - User shopping carts
- `cart_items` - Individual items in carts

## 🔧 Configuration Notes

- The application uses `spring.jpa.hibernate.ddl-auto=update` to automatically create/update database schema
- Default port is 8080 (can be changed in application.properties)
- Thymeleaf caching is disabled for development

## 🧪 Testing the Application

1. **Browse as Guest**: Visit the books page without logging in
2. **Customer Flow**: 
   - Register a new account
   - Login and add books to cart
   - Complete checkout with any 16-digit number
3. **Admin Flow**:
   - Login with admin credentials
   - Add, edit, or delete books
   - Monitor inventory changes

## ⚠️ Important Notes

- This is an educational project without real payment processing
- Credit card numbers are not validated or stored
- Order history is not maintained
- Passwords are stored in plain text (not recommended for production)

## 🤝 Contributing

This is a university assignment project. Feel free to fork and modify for your own learning purposes.

## 📄 License

This project is created for educational purposes as part of a university assignment.

## 👨‍💻 Author

- **Name**: Abdelaziz Abushark
- **GitHub**: [@azizosharke](https://github.com/azizosharke)

---

**Note**: Remember to never commit sensitive information like database passwords to version control. Always use environment variables or configuration files that are excluded from Git.
