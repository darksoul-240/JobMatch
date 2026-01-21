# JobMatch

A mini job portal application built with Spring Boot that enables employers to post jobs and job seekers to browse and apply for positions.

## Features

- **User Authentication & Authorization**
  - User registration and login
  - Role-based access control (Job Seekers and Employers)
  - Secure password handling with Spring Security

- **Job Management**
  - Browse available jobs
  - Search jobs by location/proximity
  - Post new job listings (Employers)
  - View and manage posted jobs

- **Application System**
  - Apply for jobs
  - Track application status
  - View received applications (Employers)
  - Manage your applications

## Technology Stack

- **Backend Framework:** Spring Boot 3.2.6
- **Java Version:** 21
- **Database:** PostgreSQL 15 with PostGIS extension
- **ORM:** Spring Data JPA / Hibernate
- **Security:** Spring Security
- **Template Engine:** Thymeleaf
- **Build Tool:** Maven
- **Containerization:** Docker & Docker Compose

## Prerequisites

- Java 21 or higher
- Docker and Docker Compose
- Maven (or use the included Maven Wrapper)

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd JobMatch
```

### 2. Start the Database

Start the PostgreSQL database with PostGIS extension using Docker Compose:

```bash
docker-compose up -d
```

This will start a PostgreSQL container with:
- Database: `jobmatch`
- Username: `jobuser`
- Password: `jobpass`
- Port: `5432`

### 3. Build the Application

Using Maven Wrapper (recommended):

```bash
./mvnw clean install
```

Or using Maven:

```bash
mvn clean install
```

### 4. Run the Application

Using Maven Wrapper:

```bash
./mvnw spring-boot:run
```

Or using Maven:

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Project Structure

```
JobMatch/
├── src/
│   ├── main/
│   │   ├── java/com/JobMatch/
│   │   │   ├── JobMatchApplication.java      # Main application class
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java       # Security configuration
│   │   │   ├── controller/                   # REST/MVC controllers
│   │   │   │   ├── ApplicationController.java
│   │   │   │   ├── AuthController.java
│   │   │   │   └── JobController.java
│   │   │   ├── domain/                       # Entity models
│   │   │   │   ├── Application.java
│   │   │   │   ├── Job.java
│   │   │   │   └── User.java
│   │   │   ├── repository/                   # Data access layer
│   │   │   │   ├── ApplicationRepository.java
│   │   │   │   ├── JobRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   └── service/                      # Business logic
│   │   │       ├── ApplicationService.java
│   │   │       ├── JobService.java
│   │   │       └── UserService.java
│   │   └── resources/
│   │       ├── application.properties        # Application configuration
│   │       └── templates/                    # Thymeleaf templates
│   │           ├── browse-jobs.html
│   │           ├── dashboard.html
│   │           ├── home.html
│   │           ├── jobs-nearby-form.html
│   │           ├── jobs-nearby-results.html
│   │           ├── login.html
│   │           ├── my-applications.html
│   │           ├── my-jobs.html
│   │           ├── post-job.html
│   │           ├── received-applications.html
│   │           └── register.html
│   └── test/                                 # Test classes
├── docker-compose.yml                         # Docker configuration
├── pom.xml                                    # Maven configuration
└── README.md                                  # This file
```

## Available Pages

- **Home** (`/`) - Landing page
- **Login** (`/login`) - User authentication
- **Register** (`/register`) - New user registration
- **Dashboard** (`/dashboard`) - User dashboard
- **Browse Jobs** (`/jobs`) - View all available jobs
- **Jobs Nearby** (`/jobs/nearby`) - Search jobs by location
- **Post Job** (`/jobs/post`) - Create new job listing (Employers only)
- **My Jobs** (`/my-jobs`) - View your posted jobs (Employers)
- **My Applications** (`/my-applications`) - View your job applications
- **Received Applications** (`/applications/received`) - View applications for your jobs (Employers)

## Configuration

Application settings can be modified in [src/main/resources/application.properties](src/main/resources/application.properties):

- **Database Configuration:** Connection details for PostgreSQL
- **Server Port:** Default is 8080
- **JPA Settings:** Hibernate dialect and DDL auto-generation
- **Thymeleaf:** Template caching settings

## Database Schema

The application uses PostgreSQL with PostGIS for geospatial features. Main entities:

- **User** - User accounts with authentication credentials and roles
- **Job** - Job listings with details and location information
- **Application** - Job applications linking users to jobs

## Development

### Running Tests

```bash
./mvnw test
```

### Stopping the Database

```bash
docker-compose down
```

To remove the database volume as well:

```bash
docker-compose down -v
```

## License

This project is a demonstration/educational application.

## Support

For questions or issues, please create an issue in the repository.
