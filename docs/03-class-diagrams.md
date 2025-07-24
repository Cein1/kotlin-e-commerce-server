```mermaid
    classDiagram
        class User {
            Long id
            String userId
            String email
            String name
            String gender
            LocalDate birthDate
            LocalDateTime createdAt
            LocalDateTime updatedAt
        }
    
        class Brand {
            Long id
            String name
            String description
            String thumbnail
            String homepageUrl
            LocalDateTime createdAt
            LocalDateTime updatedAt
        }
        
        class Product {
            Long id
            Brand brand
            String name
            BigDecimal price
            String thumbnail
            LocalDateTime createdAt
            LocalDateTime updatedAt
        }
        
        class Stock {
            Long id
            Product product
            Int inbound
            Int outbound
            Int available
            LocalDateTime createdAt
        }
        
        class Like {
            Product product
            User user
            LocalDateTime createdAt
        }
        
        class Point {
            Long id
            User user
            BigDecimal inbound
            BigDecimal outbound
            BigDecimal balance
            LocalDateTime createdAt
        }
        
        class Order {
            Long id
            User user
            List<OrderItem> orderItems
            LocalDateTime createdAt
        }
        
        class OrderItem {
            Long id
            Order order
            String name
            String thumbnail
            BigDecimal price
            Int quantity
            BigDecimal amount
            String status
            LocalDateTime createdAt
        }
        
        class Payment {
            Long id
            User user
            Order order
            BigDecimal amount
            String status
            LocalDateTime createdAt
            LocalDateTime updatedAt
        }
        
        Product-->Brand
        Stock-->Product
        Point-->User
        Like-->User
        Like-->Product
        OrderItem-->Order
        Order-->Payment
        User-->Order
```