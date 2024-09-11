# Facebook Clone Project

## Description
This is a Facebook clone project using Spring Boot, built with Java 17 and MySQL. The project simulates basic functionalities of the Facebook social network.

## Technologies Used
- Java 17
- Spring Boot 3.3.3
- Spring Data JPA
- Spring Security
- MySQL
- JWT (JSON Web Token)
- Maven

## Installation
1. Clone this repository to your local machine.
2. Ensure you have Java 17 and MySQL installed.
3. Configure the MySQL database in the file:

4. Run the following command to build the project:
   ```
   mvn clean install
   ```

5. To start the application, run:
   ```
   mvn spring-boot:run
   ```

## Main Features
- User registration and login
- Authentication and authorization using JWT
- Multilingual support (English and Vietnamese)
- RESTful API for basic social network functionalities

## Project Structure
- `src/main/java/com/project/facebook`: Contains Java source code
  - `configs`: Configuration classes
  - `controllers`: Request handling controllers
  - `models`: Entity classes
  - `repositories`: Repository interfaces
  - `services`: Business logic services
  - `dtos`: Data Transfer Objects
  - `responses`: Response classes
- `src/main/resources`: Contains configuration files and resources
  - `application.yml`: Main application configuration file
  - `i18n`: Language files

## API Endpoints
- Register: POST `/api/v1/users/register`
- Login: POST `/api/v1/users/login`
- View user information: GET `/api/v1/users/{phone}`

## Security
The project uses Spring Security and JWT for authentication and authorization. The main security configuration is defined in the `WebSecurityConfig` class.

## Multilingual Support
The project supports multiple languages with English as the default. To change the default language, edit the configuration in the `application.yml` file:
```
messages:
    basename: i18n/messages
    encoding: UTF-8
    default-locale: en
```

## Contributing
Contributions are welcome. Please create an issue or pull request to contribute to the project.

## License
[Add information about the project license if applicable]