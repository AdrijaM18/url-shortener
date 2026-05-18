# URL Shortener
A Spring Boot URL shortener service backed by MongoDB.

> Submitted for Roadmap.sh project: https://roadmap.sh/projects/url-shortening-service

## Features

- Create shortened URLs
- List all saved URLs
- Redirect using short code
- Update an existing URL by short code
- Delete a shortened URL by short code

## Requirements

- Java 17
- Maven (or use included `mvnw` / `mvnw.cmd`)
- MongoDB running in docker

## Configuration

The application reads MongoDB settings from `src/main/resources/application.properties`:

```properties
spring.application.name=url-shortener
spring.data.mongodb.uri=mongodb://localhost:27017/urlshortener
```

If your MongoDB instance uses a different host, port, or database name, update the URI accordingly.

## Run locally

From the project root:

```bash
./mvnw spring-boot:run
```

Or on Windows:

```powershell
mvnw.cmd spring-boot:run
```

The service starts on `http://localhost:8080`.

## API Endpoints

Base URL: `http://localhost:8080/api/v1`

### Create shortened URL

- Method: `POST`
- Endpoint: `/shorten`
- Body: JSON

```json
{
    "url": "https://www.linkedin.com/in/adrija--mukherjee"
}
```

### Get all URLs

- Method: `GET`
- Endpoint: `/urls`

### Redirect by short code

- Method: `GET`
- Endpoint: `/{shortCode}`

Example:

```http
GET http://localhost:8080/api/v1/abc123
```

### Update a shortened URL

- Method: `PUT`
- Endpoint: `/shorten/{shortCode}`
- Body: JSON

```json
{
  "url": "https://example.com/new-target"
}
```

### Delete a shortened URL

- Method: `DELETE`
- Endpoint: `/shorten/{shortCode}`

Example:

```http
DELETE http://localhost:8080/api/v1/abc123
```

## Project structure

- `src/main/java` — application source code
- `src/main/resources` — Spring Boot configuration
- `pom.xml` — Maven build configuration

## Notes

- The app uses Spring Data MongoDB to persist shortened URLs.
- Short codes are generated as random 6-character alphanumeric strings.
- If MongoDB is unavailable, the service will fail to start.
