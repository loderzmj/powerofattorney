# Exercise Power of Attorney Service

   - Build and run Power of Attorney Service from https://github.com/Aldrion/json-stub
   - Build: mvn clean install. This will run integration test as well.
   - Run app with: java -jar target/rabobank-0.0.1-SNAPSHOT.jar

# REST API

  - Two Spring Boot Web Flux endpoints:
    - Details about cards for specified power of attorney:<br>
    http://localhost:50125/rabobank/api/exercise/power-of-attorney/0001/cards
    -  Detail about card for the specified grantee:<br>
    http://localhost:50125/rabobank/api/exercise/power-of-attorney/card/2222?grantee=Fellowship%20of%20the%20ring   
  - API composition done with CompletableFutures and Project Reactor. 
  - API clients generated with swagger-maven-plugin
  - Endpoint response implemented as Immutable objects: https://immutables.github.io/immutable.html
  - Conversion from API responses to DTO implemented with http://modelmapper.org/
  - SSL implementation use Self Signed Certificate. By default is disabled.
  
  
   