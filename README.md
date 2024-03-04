Running bookstore app:

1. `cd bookstore`
2. `gradlew publishImageToLocalRegistry` to build and publish Docker image to local repository
3. `docker compose up -d` to run bookstore and mongodb containers
4. Access application with `http://localhost:8080/html/home`
5. Login with any username and password