# Spring Boot Library Application
 
## Run Spring Boot application
```
mvn spring-boot:run
```

## Notes
- Use import tool/Library.postman_collection.json and tool/Library Local.postman_environment.json in postman to help testing the end points
- /schema contains sql file to create database structure in the mysql
- Open /schema/er model/library_db.wmb in the MySql workbench to view the er diagram of the sata structure. MySQL workbench can also be used to create database in mysql.
- For the data import. Please run the end points in order of
  - upload books
  - upload users
  - upload borrowed