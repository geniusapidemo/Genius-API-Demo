# Genius-API-Demo

This demo project retrieves popular songs for a given artist name.

### Local setup:

1.  Pull code from Master repository 
2.  Open project in IntelliJ or Eclipse as a Spring Boot project
3.  Run GeniusDemoApplication class


### URL links to test the application

    http://localhost:8080/swagger-ui.html
    http://localhost:8080/genius/songs?artistName=elvis%20presley

### CURL command: 

curl -X GET --header 'Accept: application/json' 'http://localhost:8080/genius/songs?artistName=Elvis%20Presley'
