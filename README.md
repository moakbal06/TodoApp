# TodoApp

*Application Stack*
- Spring Boot, Spring Couchbase ,Swagger ,Docker,Angular,Nginx

*TodoApp Test* 
- Tests are not finished yet due to lack of time
- run "mvn test" in the TodoApp project.

 *TodoApp Build* 
- mvn clean install -DskipTest



*To start all*
-'docker-compose up' in the folder.

*Couchbase* 
-Url : http://localhost:8091/ui/index.html
-User Name:Administrator
-Password:password

*After Couchbase is launched:*

-We need to make some changes on web gui.
-Settings -> Advanced Index Settings ->  Indexer Threads = 1

-Run the query 'CREATE PRIMARY INDEX ON `default`:`todo`'
  
-And  Create todo and User buckets.

*To Access Todo App*


Ui(Frontend)
http://localhost:8081

Register and Login to start using app.

Swagger
- Documentation of apis is not finished yet due to lack of time
- http://localhost:8080/swagger-ui/
