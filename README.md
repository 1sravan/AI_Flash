
# Spring AI with Gemini & OpenAI Integration

This project demonstrates how to integrate Google Gemini and OpenAI AI with a Spring Boot application using Spring AI. The Goal is to combine Spring Boot's powerful backend capabilities with  advanced AI models,enabled intelligent decision-making,natural language understanding,and real-time responses in your application.




## Table of Contents

- [Tech Stack](#Tech-Stack)
- [Prerequisites](#Prerequisites)
- [ Environment Variables](#Environment-Variables)
- [API Reference](#API-Reference)
- [API cURLs](#API-cURLs)
  - [User Registration](#User-Registration)
  - [User Authentication and Login](#User-Authentication-and-Login)
  - [Interacting with the Gemini Service](#Interacting-with-the-Gemini-Service)
  - [Interacting with the OpenAI Service](#Interacting-with-the-OpenAI-Service)
## Tech Stack

 - **Java 18** – Programming language for backend development.
 - **Spring Boot** – Framework for building Java-based web applications.
 - **Spring Data JPA** – ORM tool for interacting with relational databases.
 - **Spring Security** – Framework for authentication and authorization
 - **JWT** – JSON Web Token for user authentication.
 - **Maven** – Build automation tool.
 - **Postman** - Tools for testing APIs.



## Prerequisites
 - Java 18 - Make sure Java 18 (or higher) is installed on your machine.
 - Basic understanding of Spring Boot – Familiarity with creating REST APIs using Spring Boot.
 - Google Gemini API and OpenAI credentials – API keys from Google Gemini and OpenAI must be obtained and configured for AI model integration.
 - Postman or cURL – Tools for testing API endpoints.

    
## Environment Variables

To run this project, you will need to add the following environment variables to your .env file

- `GEMINI_API_URL` - API URL for accessing Gemini's services.
- `GEMINI_API_KEY` - API key for Google Gemini integration.
- `OpenAI_API_KEY` - API key for accessing OpenAI's services.


## API Reference
- #### For Register the User

```http
  POST /api/signup
```

| Required | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` | `String` | **Required**. Your Username|
| `password` | `String` | **Required**. Your Password|

- #### For login the user

```http
  POST /api/login
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `username` | `String` | **Required**. Your Username|
| `password` | `String` | **Required**. Your Password|

- #### Prompt for Gemini
```http
  POST api/qa/ask
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `question` | `String` | **Required**. Your Promt/question|

- #### Prompt for OpenAI
```http
  POST api/qa/openask
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `question` | `String` | **Required**. Your Promt/question|



## API cURLs

### API cURL Commands

This section provides cURL commands for testing the API endpoints in this project.

### 1. User Registration

**Endpoint:** `POST /api/auth/signup`  
**Description:** Create an account by signing up with your username and password.

**cURL command:**
```bash
curl --location 'localhost:8080/api/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username":"radhu",
    "password":"radhu@123"
}'
```
### 2. User Authentication and Login

**Endpoint:** `POST /api/auth/login`  
**Description:** Authenticate with your username and password to receive a JWT token, which provides access to the application's services.

**cURL command:**
```bash
curl --location 'http://localhost:8080/api/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username":"radhu",
    "password":"radhu@123"
}'
```
### 3. Interacting with the Gemini Service

**Endpoint:** `POST /api/qa/ask`  
**Description:** Provide a prompt to get answer.

**cURL command:**
```bash
curl --location 'http://localhost:8080/api/qa/ask' \
--header 'Content-Type: application/json' \
--data '{
    "question": "Explain how Neutron works"
}'
```

### 4. Interacting with the OpenAI Service

**Endpoint:** `POST /api/qa/openask`  
**Description:** Provide a prompt to get answer.
```bash
curl --location 'http://localhost:8080/api/qa/openask' \
--header 'Content-Type: application/json' \
--data '{
    "question": "Explain how AI works"
}'
