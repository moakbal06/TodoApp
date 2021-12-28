# TodoApp

*Application Stack*
- Spring Boot, Spring Couchbase ,Swagger ,Docker,Angular,Nginx

*TodoApp Test* 
- run "mvn test" in the TodoApp project.

*To start all*
-'docker-compose up' in the folder.

*Couchbase* 
-Url : http://localhost:8091/ui/index.html
-User Name:Administrator
-Password:password

*After Couchbase launch:*

-We need to make some changes on web gui.
-Settings -> Advanced Index Settings ->  Indexer Threads = 1

-Run the query 'CREATE PRIMARY INDEX ON `default`:`todo`'
  
-And  Create todo and User buckets.

*To Access Todo App*


Ui(Frontend)
http://localhost:8081

Register and Login to start using app.

Swagger
- Documentation of apis not finished yet due to lack of time
http://localhost:8080/swagger-ui/
