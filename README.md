# URL shortener
URL shortening application

- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven 3.9](https://maven.apache.org)

## Java installation guide


## Build the application

```shell
./mvnw clean install
```

## Run the application locally

There are mainly two ways you can run the application.

- You can execute the `main` method of the `com.origin.test.urlshortener.UrlshortenerApplication` class.
- You can run the following command in the Terminal using Maven

```shell
./mvnw spring-boot:run
```

You can test the two endpoints using the following command with curl or use PostMan GUI

#### Shorten URL
```shell
curl -X POST http://localhost:8080/api/v1/shorten \
  -H "Content-Type: application/json" \
  -d '{"originalUrl":"https://www.originenergy.com.au/electricity-gas/plans.html"}'
```


#### Get Original URL
```shell
curl -v http://localhost:8080/api/v1/a5KoIC 
```
Note: Update the shortURLCode as necessary

### Assumptions
- 6 unique characters will be enough considering following assumptions
  - 100 URLs generated per second
  - 100 * 60 * 60 * 24 * 365 = 3.15 billion URLs for each year
  - 6^62 = 56 billion unique ids => 17 years
- This application is for vertical scaling application to avoid collision. 


### Improvements 
- Update Open API (Swagger)
- Introduce zookeper to coordinate with multiple application servers so that horizontal scaling can be done
  - Zookeper can maintain different ranges for servers which can be used to generate unique codes
- Introduce Distributed caching solution like Redis or HazelCast to avoid DB hits
- Introduce random suffix as a security improvement

