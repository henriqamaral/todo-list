# TODO-LIST

# Technologies

* Java 11
* Spring Boot 2
* Spring Cloud 2
* Spring Data MongoDB
* Swagger
* Spring Eureka
* Spring Gateway

## Building all the projects
```
./build_all.sh
```
## Starting
```
docker compose up
```
## Building all and running the projects
```
./build_run_all.sh
```

### Todo 
#### Requests Examples:
- Create a new Todo
```
curl --request POST \
  --url http://localhost:8081/todos \
  --header 'Content-Type: application/json' \
  --data '{
  "id": 1,
  "name": "todo challenge",
  "description": "todo a todo list",
  "tasks": [
    {
      "id": 1,
      "name": "implement post",
      "description": "string"
    }
  ]
}'
```

- Get Todo By ID
```
curl --request GET \
  --url http://localhost:8081/todos/1
```

- Update Todo
```
curl --request PUT \
  --url http://localhost:8081/todos/1 \
  --header 'Content-Type: application/json' \
  --data '{
  "id": 1,
  "name": "todo challenge",
  "description": "todo a todo list",
  "tasks": [
    {
      "id": 1,
      "name": "implement post"
    },
    {
      "id": 2,
      "name": "implement put",
      "description": "put todo"
    }
  ]
}'
```

- Delete Todo By ID
```
curl --request DELETE \
  --url http://localhost:8081/todos/1
```

- Get All Todos
```
curl --request GET \
  --url http://localhost:8081/todos
```
