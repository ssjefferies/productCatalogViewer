# Product Catalog Viewer

A Spring Boot REST API for managing product catalogs with full CRUD operations.

## Prerequisites

- **Java 17** or higher
- **Maven 3.6.3** or higher
- **PostgreSQL 12** or higher
- **Git** for version control

## Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/ssjefferies/productCatalogViewer.git
cd productCatalogViewer
```

### 2. Database Setup
Create a PostgreSQL database and user:
```sql
CREATE DATABASE product_catalog;
CREATE USER catalog_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE product_catalog TO catalog_user;
```

### 3. Application Configuration
Create or update `src/main/resources/application.properties`:
```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/product_catalog_viewer_db?currentSchema=product_catalog
spring.datasource.username=postgres
spring.datasource.password=pgpass123!
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Server Configuration
server.port=8080

# Actuator Configuration
management.endpoints.web.exposure.include=health,info
```

### 4. Build and Run
```bash
# Build the application
mvn clean install

# Run the application
mvn spring-boot:run

# Or run the JAR file
java -jar target/productCatalogViewer-0.0.1-SNAPSHOT.jar
```

### 5. Verify Installation
- Application should be running on: http://localhost:8080
- Health check: http://localhost:8080/actuator/health

## API Overview

Base URL: `http://localhost:8080/api`

### Endpoints

#### Products
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/products` | Get all products | None | `List<ProductDTO>` |
| GET | `/products/{productKey}` | Get product by product key | None | `ProductDTO` |
| POST | `/products` | Create new product | `ProductDTO` | `ProductDTO` (201 Created) |

#### Brand Summary
| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/products/brand-summary` | Get brand summary statistics | None | `List<BrandSummaryDTO>` |

### Data Models

#### ProductDTO
```json
{
  "product_key": 1,
  "retailer": "Best Buy",
  "brand": "Apple",
  "model": "iPhone 15",
  "product_name": "iPhone 15 Pro",
  "product_description": "Latest iPhone model with advanced features",
  "price": 999.99
}
```

**Note**: When creating a new product, the `id` field is NOT auto-generated, and must be included when saving a new product.

#### BrandSummaryDTO
```json
{
  "brand": "Apple",
  "count": 10
}
```

### Example API Calls

#### Get All Products
```bash
curl -X GET http://localhost:8080/api/products
```

#### Get Product by Product Key
```bash
curl -X GET http://localhost:8080/api/products/1
```

#### Get Brand Summary
```bash
curl -X GET http://localhost:8080/api/products/brand-summary
```

#### Create a New Product
```bash
curl --location 'http://localhost:8080/api/products' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data '{
    "product_key": 12952636,
    "retailer": "XYZ Retail",
    "brand": "XYZ Brand",
    "model": "BH60071",
    "product_name": "63.5-in x 41-in x 98-in Steel Log Rack",
    "product_description": "This is the product Description",
    "price": 5.98
}'
```

**Success Response (201 Created):**
```json
{
    "retailer": "XYZ Retail",
    "brand": "XYZ Brand",
    "model": "BH60071",
    "price": 5.98,
    "product_key": 12952636,
    "product_name": "63.5-in x 41-in x 98-in Steel Log Rack",
    "product_description": "This is the product Description"
}
```

**Error Response (400 Bad Request):**
```json
"Error creating product: Validation failed for field 'productName'"
```

## Error Handling

The API returns standard HTTP status codes:

- `200 OK` - Successful GET requests
- `201 Created` - Successful POST requests
- `400 Bad Request` - Invalid request data or validation errors
- `404 Not Found` - Product not found (returns empty ProductDTO)
- `500 Internal Server Error` - Server error

### Error Response Format
For validation errors or server errors, the API returns a simple error message:
```json
"Error creating product: [specific error message]"
```

**Note**: Based on your controller implementation:
- GET endpoints return data directly (200 OK)
- POST endpoint returns 201 Created on success, 400 Bad Request on error
- Invalid product keys may return an empty ProductDTO rather than 404

## Development

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=ProductServiceTest
```

### Database Schema
The application automatically creates the following table structure:

```sql
CREATE TABLE product (
    product_key SERIAL PRIMARY KEY,
    retailer VARCHAR(100),
    brand VARCHAR(100),
    model VARCHAR(50),
    product_name VARCHAR(500),
    product_description TEXT,
    price NUMERIC(8, 2) DEFAULT 0
);
```

**Note**: The `product_key` field serves as the primary key and is auto-generated.

### CORS Configuration
The application is configured to allow CORS requests from:
- `http://localhost:3000` (React development server)

## Production Deployment

### Environment Variables
Set the following environment variables for production:
```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://your-db-host:5432/product_catalog
export SPRING_DATASOURCE_USERNAME=your_username
export SPRING_DATASOURCE_PASSWORD=your_password
export SERVER_PORT=8080
```

### Docker Deployment
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/productCatalogViewer-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

Build and run:
```bash
docker build -t product-catalog-api .
docker run -p 8080:8080 product-catalog-api
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-feature`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature/new-feature`)
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contact

- **Author**: Your Name
- **Email**: your.email@example.com
- **GitHub**: https://github.com/your-username