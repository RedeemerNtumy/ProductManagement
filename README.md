# Product Management System Design Documentation

## Overview

This document outlines the architecture and component design of the Product Management System. The system is developed using Spring Boot and utilizes Java Persistence API (JPA) with MySQL for data management. The primary focus is on handling categories, subcategories, and products in a hierarchical structure.

## Architecture Diagram

Below is the architecture diagram illustrating the main components of the system and their interactions:

```plaintext
+---------------------+        +---------------------+
|   CategoryController|        |   ProductController |
+---------+-----------+        +----------+----------+
          |                                  |
          v                                  v
+---------+-----------+        +------------+---------+
|   CategoryService   |        |   ProductService     |
+---------+-----------+        +------------+---------+
          |                                  |
          v                                  v
+---------+-----------+        +------------+---------+
|   CategoryRepository|        |   ProductRepository  |
+---------+-----------+        +------------+---------+
          |                                  |
          v                                  v
+---------+-----------+        +------------+---------+
|       MySQL Database        |        MySQL Database |
+---------------------+        +----------------------+
```
## Component Descriptions
### Controllers

- **CategoryController**: Manages HTTP requests related to categories. It provides endpoints for adding, updating, deleting, and retrieving category data.
- **ProductController**: Handles HTTP requests for product operations, including adding, updating, deleting, and retrieving products.

### Services

- **CategoryService**: Encapsulates the business logic related to category management. It interacts with the `CategoryRepository` to perform CRUD operations.
- **ProductService**: Manages the business logic for product operations. It utilizes `ProductRepository` for data access and manipulation.

### Repositories

- **CategoryRepository**: Interface for database operations related to categories. It extends `JpaRepository` to leverage Spring Data JPA functionalities.
- **ProductRepository**: Provides an abstraction layer to perform CRUD operations on the product data. It also extends `JpaRepository`.

### Models

- **Category**: Represents the category entity with attributes such as name and a list of subcategories.
- **Subcategory**: Embedded within the Category entity, representing subcategories that can contain products.
- **Product**: Represents the product entity, containing attributes like name, price, and description.

## Data Model

### Category

- **id**: Unique identifier for the category.
- **name**: Name of the category.
- **subcategories**: List of subcategories under this category.

### Subcategory

- **id**: Unique identifier for the subcategory.
- **name**: Name of the subcategory.
- **products**: List of products within the subcategory.

### Product

- **id**: Unique identifier for the product.
- **name**: Name of the product.
- **description**: Description of the product.
- **price**: Price of the product.

## Persistence Layer

The system uses JPA repositories to interact with the MySQL database, providing a high-level abstraction for database operations.

