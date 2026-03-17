# Maddie Lab

A Maven-based Java project with a standard project structure.

## Quick Start

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Build
```bash
mvn clean compile
```

### Test
```bash
mvn test
```

### Run
```bash
mvn exec:java -Dexec.mainClass="App"
```

### Package
```bash
mvn clean package
```

## Project Structure

```
.
├── src/
│   ├── main/
│   │   ├── java/          # Source code
│   │   └── resources/     # Configuration and resources
│   └── test/
│       ├── java/          # Unit tests
│       └── resources/     # Test resources
├── target/                # Build output (generated)
├── pom.xml                # Maven configuration
└── README.md              # This file
```

## Development

Edit the source files in `src/main/java/` and tests in `src/test/java/`. Maven will automatically compile and test your code.

## License

This project is open source.
