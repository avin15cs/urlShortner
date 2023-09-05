# URL Shortener Service

A simple URL shortener service built with Spring Boot and Java.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
  - [Shortening URLs](#shortening-urls)
  - [Redirecting URLs](#redirecting-urls)
- [Configuration](#configuration)
- [Contributing](#contributing)
- [License](#license)

## Introduction

URL Shortener Service is a web application that allows you to shorten long URLs into easy-to-share and manage short URLs. It's built using the Spring Boot framework and Java. The service also provides the ability to expire URLs after a certain time period.

## Features

- Shorten long URLs into short, unique codes.
- Redirect users from short URLs to their original long URLs.
- Expiry of URLs after a specified time (default: 1 year).
- Error handling for non-existent and expired URLs.
- RESTful API for URL shortening and redirection.

## Getting Started

### Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) installed (Java 8 or higher).
- Maven or Gradle for building the project.
- A database (e.g., MySQL, PostgreSQL) for storing URL mappings.

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/avin15cs/url-shortener.git
   cd url-shortener
2. Build the project using Maven or Gradle:

  ```bash
  # Using Maven
  mvn clean install
  ```

3. Configure your database connection in application.properties or application.yml.

4. Run the application:

  ```bash
  # Using Maven
  mvn spring-boot:run
  ```

Usage
Shortening URLs
To shorten a long URL, make a POST request to the /shorten endpoint with the long URL in the request body.

Example using curl:

  ```bash
  curl -X POST -H "Content-Type: application/json" -d '{"longUrl": "https://www.example.com/very/long/url"}' http://localhost:8080/shorten
  ```
Redirecting URLs
To access a shortened URL, simply use the short URL as a path in your browser or client.

Example:

Short URL: http://localhost:8080/abc123
Redirects to: https://www.example.com/very/long/url
Configuration
You can customize the behavior of the URL shortener service by modifying the application.properties file. This includes database configuration, URL expiration settings, and more.

Contributing
Contributions are welcome! If you have suggestions, bug reports, or want to add new features, please submit an issue or create a pull request.

License

```bash
This project is licensed under the MIT License - see the LICENSE.md file for details.



