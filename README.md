# TodoApp
Couchbase 
Url : http://localhost:8091/ui/index.html
User Name:Administrator
Password:password

After Couchbase launch:

We need to make some changes on web gui.
Settings -> Advanced Index Settings ->  Indexer Threads = 1

Run the query 'CREATE PRIMARY INDEX ON `default`:`todo`'
  
And  Create todo and User buckets.

To Access Todo App
'docker-compose up' in the folder.

Ui(Frontend)
http://localhost:8081

Register and Login to start using app.

Swagger
http://localhost:8080/swagger-ui/
