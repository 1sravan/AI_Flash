
# Spring AI with Gemini Integration

This project demonstrates how to integrate Google Gemini AI with a Spring Boot application using Spring AI. The Goal is to combine Spring Boot's powerful backend capabilities with Gemini's advanced AI models, enabled intelligent decision-making, natural language understanding, and real-time responses in your application.




## Prerequisites
 - Java 18
 - Spring Boot
 - Maven
 - Google Gemini API credentials
 - A basic understanding of Spring Boot and REST APIs

    
## Environment Variables

To run this project, you will need to add the following environment variables to your .env file

`GEMINI_API_URL`

`GEMINI_API_KEY`


## CURL
```bash
curl --location 'http://localhost:8080/api/qa/ask' \
--header 'Content-Type: application/json' \
--data '{
    "question": "Tell me about Gemini Flash"
}'
