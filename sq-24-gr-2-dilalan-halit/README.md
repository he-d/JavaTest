
## E-Store

This project is a simple e-commerce template for the Proseminar 
"[Software Quality](https://lfuonline.uibk.ac.at/public/lfuonline_lv.details?sem_id_in=24S&lvnr_id_in=703086)"
at the University of Innsbruck. The goal is to provide students a project onto which they can apply different
software quality techniques and technologies. 

The project gives the web user the possibility to view products, add them to the cart and to check out the order.
For ordering a product the user needs to be logged in. An authentication mechanism over JWT and cookies is implemented.
The web application should also work on mobile devices and tablets as the UI adapts to the screen dimensions.

Please note that this project is not intended for production use and is only a template for educational purposes.
It is therefore intended to be suboptimal in certain areas, such as security, performance and usability to allow 
students to improve the code base during the semester.

### Copyright
Copyright (C) Harald Victor Schweiger, B.Sc. M.Sc. - All Rights Reserved All information contained herein is,
and remains the property of the author. Unauthorized copying, sharing or using this file,
via any medium is strictly prohibited unless prior written permission is obtained from the author.
Proprietary and confidential. Written by Harald Victor Schweiger <harald.schweiger@uibk.ac.at>, January 2024

### Technology Stack
- Backend
    - Spring Boot 3.2.2
    - Spring Security 6.2.1
    - Lombok
    - Jakarta Persistence API
    - H2 database
- Middleware & Frontend
  - TypeScript
  - SvelteKit
  - TailwindCSS

### Run Backend
Prerequisites: 
- Java 21
- Maven 3.3.2 (provided over `./backend/mvnw`)

Build and run the backend with the following commands:
1. `cd backend`
2. `mvn clean install`
3. `mvn spring-boot:run`

Normally, the IDEA will recognize the `pom.xml` and provide a run configuration automatically.
Otherwise, you might need to modify your project structure.

The backend is accessible via [http://localhost:8080/](http://localhost:8080/).
You can check if the backend works by calling the [rest api](http://localhost:8080/api/products) for the top products.

The H2 database is accessible via [http://localhost:8080/h2-console](http://localhost:8080/h2-console).
The JDBC URL is `jdbc:h2:mem:testdb` and the username is `sa` with password `password` as defined in `application.properties`.


### Run Frontend
Prerequisites:
- NodeJS v20.11.0
- npm 10.2.4

The frontend and middleware can be installed and run with the following commands:
1. `cd frontend`
2. `npm install`
3. `npm run dev`

It is advisable to open `package.json` as IDEA will recognize the `vite` commands and provide a run configuration.

The project uses vite as a bundler, which enables hot module replacement and fast builds.
This means you can work on the frontend and see the changes in the browser without having to reload the page.

The application is accessible via [http://localhost:5173/](http://localhost:5173/).
You can check out the [about](http://localhost:5173/about) page, as this is one of the view pages that do not require 
the backend to be running.


## Sheet 03 Content Update

### JwtService
New and improved functionality to `JwtService` and `JwtServiceImpl`
- Usage of spring post construct to load the `secure` key and to avoid recreating the algorithm instance all the time.
- `extractUserName` signature informs about the possibility of a `JWTVerificationException`
- New Method `Instant extractExpiresAt(String token) throws JWTVerificationException;`
- New Method`String extractIssuer(String token) throws JWTVerificationException;`
- New Method `String extractRoles(String token) throws JWTVerificationException;`

### pom.xml
- Removed runtime scope from h2 dependency to chain access to the h2 api.
- Added `junit-jupiter-api` to the test scope.
- Added `mockito-core` to the test scope.
- Added `mockito-junit-jupiter` to the test scope.
- Added newest `maven-compiler-plugin`. *This change is optional.*
- Added `maven-surefire-plugin` to execute test over maven.
  `-XX:+EnableDynamicAgentLoading` is added to avoid a warning message when using Mockito in the current version.

### OrderStatusUpdateTrigger
- Added a new package `trigger` for h2 database triggers.
- Added a new trigger `OrderStatusUpdateTrigger` to decrement the product stock as soon as the `order_status`
  changes to SHIPPED
- Added `TriggerInitializer` to initialize the trigger on the h2 database inside the `init` package.

### Notification
- Added a new field `notification` to the user model to know if the user wants to receive email notifications.
- Added `"notification": true` to `user.json` in the resources folder; *needs to be done for each user*.
- Added `request.getNotification()` to the instance create of the user inside the `register` method of the 
  `UserControllerImpl`. *The frontend already implemented the notification, so no changes are needed there.*
- Added a new Interace `UserEmailService` to send emails to the user.
  *There is currently no implementation for this class.*
- Added a new Interface UserNotificationService and its implementation `UserNotificationServiceImpl`:
  - Method `notifyAboutLatestProducts` sends an email to all users that want to receive notifications about new products.
  - Method `subscribe` adds a user to the list of users that want to receive notifications.
    *The user should receive a confirmation email*
  - Method `unsubscribe` removes a user from the list of users that want to receive notifications.
    *The user should receive a confirmation email as well*
- Added `Iterable<User> findByNotificationTrue();` in the `UserRepository` to get all users that want to receive 
  notifications.
