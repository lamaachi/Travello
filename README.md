# Travello - Travel Agency Application

This is a web application for a travel agency, built with Java and Spring Boot.

## Table of Contents

- [About](#about)
- [Features](#features)
- [Technologies](#technologies)
- [Setup](#setup)
- [Usage](#usage)

## About

Travello is a comprehensive web platform designed to streamline the operations of a travel agency. It provides functionalities for managing travels, reservations, clients, and more.

## Features

- User authentication and authorization
- Travel listings and detailed views
- Booking and reservation management
- Client management
- Admin panel for content management

## Technologies

- **Backend:** Java, Spring Boot, Spring Security
- **Frontend:** HTML, CSS, JavaScript, Thymeleaf
- **Database:** (Please specify - e.g., MySQL, PostgreSQL, H2)
- **Build Tool:** Maven

## Setup

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/lamaachi/Travello.git
    cd Travello
    ```

2.  **Configure the database:**
    - Open `src/main/resources/application.properties`.
    - Update the `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` properties with your database credentials.

3.  **Build the project:**
    ```bash
    ./mvnw clean install
    ```

4.  **Run the application:**
    ```bash
    ./mvnw spring-boot:run
    ```
    The application will be accessible at `http://localhost:8080`.

## Usage

- Access the application through your web browser.
- Register a new account or log in with existing credentials.
- The admin panel can be used to manage the application's content.
