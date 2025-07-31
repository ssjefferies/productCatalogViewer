# Product Catalog Viewer

A Spring Boot REST API for managing product catalogs with full CRUD operations.
(Also see the README.md in the ./app folder for information about the UI portion of the app).

[Demonstration Video](https://youtu.be/SmJ1RkTpNnw)

## Prerequisites

### For Docker Setup (Recommended)
- **Docker** 20.10 or higher
- **Docker Compose** 2.0 or higher

### For Local Development
- **Java 17** or higher
- **Maven 3.6.3** or higher
- **PostgreSQL 12** or higher
- **Git** for version control


## Project Structure
```
productCatalogViewer/
├── src/
│   ├── main/java/com/ssjdev/productCatalogViewer/
│   │   ├── controller/     # REST controllers
│   │   ├── service/        # Business logic
│   │   ├── model/          # JPA entities
│   │   ├── dto/            # Data Transfer Objects
│   │   └── repository/     # Data access layer
│   └── test/               # Unit and integration tests
├── Dockerfile              # Container build instructions
├── docker-compose.yml      # Development environment
├── init.sql               # Sample data (optional)
├── pom.xml                # Maven dependencies
└── README.md              # This file
```

## Setup Instructions

### Option 1: Docker Setup (Recommended)

#### Quick Start
```bash
# Clone and start
git clone https://github.com/your-username/productCatalogViewer.git
cd productCatalogViewer
docker-compose up --build
```

#### Docker Commands
```bash
# Start all services in background
docker-compose up --build -d

# View logs
docker-compose logs -f app
docker-compose logs -f postgres

# Stop services
docker-compose down

# Stop and remove data volumes
docker-compose down -v

# Rebuild just the application
docker-compose build app
```

#### Access Points
- **API Base URL**: http://localhost:8080/api
- **Health Check**: http://localhost:8080/actuator/health
- **Database**: localhost:5432 (from host machine)
- **pgAdmin** (Database UI): http://localhost:5050
  - Email: admin@example.com
  - Password: admin

### Option 2: Local Development Setup

#### 1. Clone the Repository
```bash
git clone https://github.com/ssjefferies/productCatalogViewer.git
cd productCatalogViewer
```

#### 2. Database Setup
Create a PostgreSQL database and user:
```sql
CREATE DATABASE product_catalog;
CREATE USER catalog_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE product_catalog TO catalog_user;
```

#### 3. Application Configuration
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
For validation errors that occur when a new Product is Posted, the API returns a list of field names,
each with an associated error message:
{
    "price": "Price must have at most 7 integer digits and 4 fraction digits",
    "model": "Model can be up to 50 characters at most"
}

For server errors, the API returns a simple error message:
```json
"Error creating product: [specific error message]"
```

- GET endpoints return data directly (200 OK)
- POST endpoint returns 201 Created on success, 400 Bad Request on error
- Invalid product keys will return a 404

## Development

### Docker Development Workflow
```bash
# Start development environment
docker-compose up -d postgres pgadmin

# Run application locally (connects to Docker database)
mvn spring-boot:run

# Or run everything in Docker
docker-compose up --build
```

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=ProductServiceTest
```

### Database Management
- **pgAdmin**: http://localhost:5050 (when using Docker)
- **Direct connection**: localhost:5432
  - Database: product_catalog
  - Username: catalog_user
  - Password: catalog_password (Docker) / your_password (local)

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


### Docker Production Setup

#### 1. Environment Variables
Create a `.env` file for production:
```bash
# Database
DB_PASSWORD=your_secure_password
DB_NAME=product_catalog
DB_USER=catalog_user

# Application
SPRING_PROFILES_ACTIVE=prod
SERVER_PORT=8080
```

#### 2. Production Docker Compose
```yaml
# docker-compose.prod.yml
version: '3.8'
services:
  postgres:
    image: postgres:15-alpine
    environment:
      POSTGRES_DB: ${DB_NAME}
      POSTGRES_USER: ${DB_USER}
      POSTGRES_PASSWORD: ${DB_PASSWORD}
    volumes:
      - postgres_data:/var/lib/postgresql/data
    restart: unless-stopped

  app:
    build: .
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/${DB_NAME}
      SPRING_DATASOURCE_USERNAME: ${DB_USER}
      SPRING_DATASOURCE_PASSWORD: ${DB_PASSWORD}
      SPRING_PROFILES_ACTIVE: ${SPRING_PROFILES_ACTIVE}
    ports:
      - "${SERVER_PORT}:8080"
    depends_on:
      - postgres
    restart: unless-stopped

volumes:
  postgres_data:
```

#### 3. Deploy to Production
```bash
# Build and deploy
docker-compose -f docker-compose.prod.yml up --build -d

# Check logs
docker-compose -f docker-compose.prod.yml logs -f
```

### Traditional Deployment

#### Environment Variables
Set the following environment variables for production:
```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://your-db-host:5432/product_catalog
export SPRING_DATASOURCE_USERNAME=your_username
export SPRING_DATASOURCE_PASSWORD=your_password
export SERVER_PORT=8080
export SPRING_PROFILES_ACTIVE=prod
```

#### JAR Deployment
```bash
# Build the JAR
mvn clean package -DskipTests

# Run in production
java -jar target/productCatalogViewer-0.0.1-SNAPSHOT.jar
```

## Troubleshooting

### Common Docker Issues

#### Port Already in Use
```bash
# Check what's using port 8080 or 5432
lsof -i :8080
lsof -i :5432

# Stop conflicting services
docker-compose down
# Kill specific processes if needed
```

#### Database Connection Issues
```bash
# Check if database is healthy
docker-compose ps
docker-compose logs postgres

# Reset database
docker-compose down -v
docker-compose up postgres
```

#### Application Won't Start
```bash
# Check application logs
docker-compose logs app

# Rebuild without cache
docker-compose build --no-cache app
docker-compose up app
```

### Common Development Issues

#### Maven Build Fails
```bash
# Clean and rebuild
mvn clean install -DskipTests

# Update dependencies
mvn dependency:resolve
```

#### Database Schema Issues
```bash
# Reset schema (WARNING: deletes data)
# Set spring.jpa.hibernate.ddl-auto=create-drop in application.properties
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

- **Author**: Sean Jefferies
- **Email**: email@seanjefferies.com
- **GitHub**: https://github.com/ssjefferies



## What to Improve
 - Add authentication to the application and authorization settings for each api endpoint
 - Improve UI styling (by a lot!)
 - Improve input validation on both front and back end
 - Add error highlighting to each input field in the Add Product page
