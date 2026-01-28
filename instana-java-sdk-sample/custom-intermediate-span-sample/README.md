# Custom Intermediate Span Sample

This sample demonstrates how to use the Instana Java SDK to create custom intermediate spans in a Spring Boot application with MongoDB.

## Prerequisites

- **Java 11** or higher
- **MongoDB** installed and running

## Setup MongoDB

Install MongoDB (macOS):
```bash
brew tap mongodb/brew
brew install mongodb-community
brew services start mongodb-community
```

To check if MongoDB is running:
```bash
brew services list
```

## Build and Run

1. Build the application from the project root:
   ```bash
   mvn clean install
   ```

2. Run the application:
   ```bash
   cd instana-java-sdk-sample/custom-intermediate-span-sample
   java -jar target/custom-intermediate-span-sample-1.2.0.jar
   ```

3. The application will start on port **8081**

4. Access the Swagger UI at: http://localhost:8081/swagger-ui.html

5. Monitor the application in Instana to see custom intermediate spans

## Configuration

- **Java Version**: 11
- **Spring Boot Version**: 2.7.18
- **Port**: 8081
- **MongoDB**: localhost:27017