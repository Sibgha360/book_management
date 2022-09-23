# Spring Boot Library Application
 

1. Synchronize the ER Model with the mySQL Server with MySQL workbench. (or alternatively user SQL Queries to create MySQL schema)
2. Run Spring Boot application
```
mvn spring-boot:run
```
3. Once the database is ready. Import the CSV data using the rest endpoint /import_data (Please see the postman documentation for end point specs). 
```
localhost:8080/api/csv/upload_data
```

4. Use postman to test the rest of the end point

