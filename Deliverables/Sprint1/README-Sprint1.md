# Sprint 1

## Purpose

This document serves the purpose to documentate certain aspects of the application developed in this sprint 1, the pipeline and the way of work.

## Objectives

In this sprint 1, the start of the implementation phase of secure software development life cycle was the main objective.

In more detail, sprint 1 aims to develop some use cases of the application documentated before, development of the application pipeline having in mind security with tools described before to achive SAST, DAST and IAST.

It was also configured some properties in terms of security, such as using https.

## Run the application

Node is needed and used to create scripts for the managment of the application.

The backend application is run locally by writing the command `./mvnw`, however, it is intended to start the application with `./mvnw "-Pdev,tls"` so we have our TLS profile running.

For the frontend, the application is run nby the command `npm run webapp:dev`.

## Accomplished

Some basic UCs implementation were accomplished, such as creation/edition of users, service requests, transports and also the development of the pipeline using SonarQube, OWASP ZAP and runing the tests developed for the application. Achieving the use of some tools of SAST and IAST. Finally the pipeline also does the deployment in a free service available online.

Adding to this, the security configuration was also achivied, including the authorization, authentication and encrypt algorithms for sensible data, such as passwords.

## Domain-driven Design
As it was used JHipster to generate the application, the Domain-driven Design (DDD) wasn't implemented initially because one con about JHipster is that it is not compatible with this methodology.

So, there were made some changes on the application in order to have DDD applied on it:
- Resource Classes to Controller
  - JHipster generates Resource classes instead of Controllers, so those classes was changed to Controller.
- Repositories interfaces
  - It was created interfaces in order to have a standardized interface for each Entity Root.
- Value Objects
  - JHipster is not compatible with the usage of Value Objects. So, it was necessary to change the entire domain to be able to use them.
- Entity Roots
  - As DDD wasn't implemented initially, the concept of Entity Root and Aggregates didn't exist aswell. So, this required some changes specially on ServiceRequest and ServiceStatus entity. Because the ServiceRequest entity, being the root, is responsible for every change on its aggregate.
  - This included the removal of Repositories, Services and Controllers of ServiceStatus and the correction on ServiceRequest classes, in order to manage the ServiceStatus entity.
- Correct separation of Folders
  - The "application/controller" includes all the Controllers of each entity root.
  - The "infrastructure/repository" was created to have the Interface repositories for each Entity Root.
  - The "domain" folder includes the Service, Value objects and the Domain classes.

# Security Configuration

By integrating Spring Security, our application benefits from a comprehensive and customizable security framework. This includes CSP, CORS, JWT token authentication, and robust password encryption, ensuring the application is secure against various threats and vulnerabilities.

The security configuration is managed in the `SecurityConfiguration.java` class. This setup includes Content Security Policy (CSP), Cross-Origin Resource Sharing (CORS), permission policies, and endpoint access controls.

### Key Security Features

1. **CSP and CORS Configuration**:
   - **CSP (Content Security Policy)**: Protects against XSS (Cross-Site Scripting) attacks by specifying trusted sources for content. Defined in the YAML configuration as:
     ```yaml
     jhipster:
       security:
         content-security-policy: "default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:"
     ```

###### default-src 'self':

- Only content (e.g., scripts, styles, images) from the same origin (the application's own domain) is allowed by default. This is a general fallback directive for other content types not explicitly specified.

###### frame-src 'self' data::

- Allows the application to display content in iframes from the same origin and from `data:` URIs. This can be useful for embedding small amounts of content directly within the HTML document.

###### script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com:

- Scripts can be loaded and executed from the same origin (self).
- `unsafe-inline` allows inline JavaScript to be executed, which can pose security risks but may be necessary for certain functionalities.
- `unsafe-eval` allows the use of eval() and similar methods for executing code, which can also pose security risks.
- Scripts from https://storage.googleapis.com are explicitly allowed, enabling the application to load scripts stored there.

###### style-src 'self' 'unsafe-inline':

- Styles can be loaded from the same origin (`self`).
- `unsafe-inline` allows inline CSS styles to be applied, which can be a security risk but may be necessary for certain functionalities.

###### img-src 'self' data::

- Images can be loaded from the same origin (`self`).
- `data:` URIs are allowed for images, enabling the embedding of small images directly within the HTML document.

###### font-src 'self' data::

- Fonts can be loaded from the same origin (`self`).
- `data:` URIs are allowed for fonts, enabling the embedding of fonts directly within the HTML document.

###### Security Implications

- 'self': Restricts most resources to be loaded only from the same origin, reducing the risk of cross-site scripting (XSS) and other injection attacks.
- 'unsafe-inline' and 'unsafe-eval': While necessary for some applications, these directives significantly weaken the policy by allowing inline scripts and the use of eval(), which can make the application vulnerable to XSS attacks.
- Specific External Sources: Allowing scripts from specific external sources (like https://storage.googleapis.com) ensures that only trusted sources are used.

   - **CORS (Cross-Origin Resource Sharing)**: Controls how resources are shared between different origins to enhance security. Defined as:
     ```yaml
     jhipster:
       cors:
         allowed-origins: "http://localhost:8100,http://localhost:9000"
         allowed-methods: "*"
         allowed-headers: "*"
         exposed-headers: "Authorization,Link,X-Total-Count,X-${jhipster.clientApp.name}-alert,X-${jhipster.clientApp.name}-error,X-${jhipster.clientApp.name}-params"
         allow-credentials: true
         max-age: 1800
     ```

2. **Endpoint Authorization**:
   - Configures which endpoints require authorization, which are public, and which require specific roles.
   - Example configuration in `SecurityConfiguration.java`:
     ```java
     @Bean
     public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
         http
             .cors(withDefaults())
             .csrf(csrf -> csrf.disable())
             .addFilterAfter(new SpaWebFilter(), BasicAuthenticationFilter.class)
             .headers(headers -> headers
                 .contentSecurityPolicy(csp -> csp.policyDirectives(jHipsterProperties.getSecurity().getContentSecurityPolicy()))
                 .frameOptions(FrameOptionsConfig::sameOrigin)
                 .referrerPolicy(referrer -> referrer.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
                 .permissionsPolicy(permissions -> permissions.policy("camera=(), fullscreen=(self), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), midi=(), payment=(), sync-xhr=()"))
             )
             .authorizeHttpRequests(authz -> authz
                 .requestMatchers(mvc.pattern("/index.html"), mvc.pattern("/*.js"), mvc.pattern("/*.txt"), mvc.pattern("/*.json"), mvc.pattern("/*.map"), mvc.pattern("/*.css")).permitAll()
                 .requestMatchers(mvc.pattern("/*.ico"), mvc.pattern("/*.png"), mvc.pattern("/*.svg"), mvc.pattern("/*.webapp")).permitAll()
                 .requestMatchers(mvc.pattern("/app/**")).permitAll()
                 .requestMatchers(mvc.pattern("/i18n/**")).permitAll()
                 .requestMatchers(mvc.pattern("/content/**")).permitAll()
                 .requestMatchers(mvc.pattern("/swagger-ui/**")).permitAll()
                 .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/authenticate")).permitAll()
                 .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/authenticate")).permitAll()
                 .requestMatchers(mvc.pattern("/api/register")).permitAll()
                 .requestMatchers(mvc.pattern("/api/activate")).permitAll()
                 .requestMatchers(mvc.pattern("/api/account/reset-password/init")).permitAll()
                 .requestMatchers(mvc.pattern("/api/account/reset-password/finish")).permitAll()
                 .requestMatchers(mvc.pattern("/api/admin/**")).hasAuthority(AuthoritiesConstants.ADMIN)
                 .requestMatchers(mvc.pattern("/api/**")).authenticated()
                 .requestMatchers(mvc.pattern("/v3/api-docs/**")).hasAuthority(AuthoritiesConstants.ADMIN)
                 .requestMatchers(mvc.pattern("/management/health")).permitAll()
                 .requestMatchers(mvc.pattern("/management/health/**")).permitAll()
                 .requestMatchers(mvc.pattern("/management/info")).permitAll()
                 .requestMatchers(mvc.pattern("/management/prometheus")).permitAll()
                 .requestMatchers(mvc.pattern("/management/**")).hasAuthority(AuthoritiesConstants.ADMIN)
             )
             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
             .exceptionHandling(exceptions -> exceptions
                 .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                 .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
             )
             .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()));
         return http.build();
     }
     ```

## Authentication and Authorization

Our application uses JWT tokens with OAuth2 for authentication and authorization, configured in the `SecurityJwtConfiguration` class. This setup ensures secure token-based authentication.

### Example JWT Token

```plaintext
eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTcxNjE1Mzc2MCwiYXV0aCI6IlJPTEVfQURNSU4iLCJpYXQiOjE3MTYwNjczNjB9.qdt758QLnnIPeMlmCZyxMWTlKe8WbUw6Mi3dY1NIRErWoSzCKHs9ZpQ-8o3_rBD43LV53imloWpiKQlsBJJdfw
```
## Password Encryption

For password encryption, we use the  `org.springframework.security.crypto.password.PasswordEncoder` from Spring Security. In the `UserService.java` class, passwords are encrypted using the AES-256 algorithm before being stored in the database. Decryption is also handled by the same library to ensure secure access.

```java
String encryptedPassword = passwordEncoder.encode(password);
```

## UUID for Identifiers
All IDs in our application utilize the `UUID` class, which ensures unique and secure identification for entities


## GitHub Actions

GitHub Actions is a continuous integration and continuous delivery (CI/CD) platform that allows you to automate your build, test, and deployment pipeline. You can create workflows that build and test every pull request to your repository, or deploy merged pull requests to production.

### Configuration Created

In order to create workflows, a folder named __.github__ was created with a folder inside called __workflows__. The workflows folder is where you need to add the worklfow files (yml files) in order to be recognized by GitHub Actions.
Two workflows were created, one that does most of the work and an extra wokflow file specifically for OWASP ZAP scan (the reason for it will be explained later).
All of private information (tokens, passwords, webhooks) were hidden from the workflow files by using GitHub repository Secrets.

### Application CI

This workflow is the workflow that does most of the heavy lifting.

```yaml
name: Application CI
on: 
  [push, pull_request,workflow_dispatch]
jobs:
  pipeline:
    name: TruckMotion pipeline
    runs-on: ubuntu-latest
    if: "!contains(github.event.head_commit.message, '[ci skip]') && !contains(github.event.head_commit.message, '[skip ci]') && !contains(github.event.pull_request.title, '[skip ci]') && !contains(github.event.pull_request.title, '[ci skip]')"
    timeout-minutes: 40
    env:
      NODE_VERSION: 20.12.2
      SPRING_OUTPUT_ANSI_ENABLED: DETECT
      SPRING_JPA_SHOW_SQL: false
      JHI_DISABLE_WEBPACK_LOGS: true
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-node@v4
        with:
          node-version: 20.12.2
      - uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: 17
      - name: Install Node.js packages
        working-directory: ./Deliverables/Sprint1/Sprint1_TruckMotion
        run: npm install
      - name: Run backend test
        working-directory: ./Deliverables/Sprint1/Sprint1_TruckMotion
        run: |
          chmod +x mvnw
          npm run ci:backend:test
      - name: Run frontend test
        working-directory: ./Deliverables/Sprint1/Sprint1_TruckMotion
        run: npm run ci:frontend:test
      - name: Cache SonarCloud packages
        uses: actions/cache@v3
        with:
          path: ~/.sonar/cache
          key: ${{ runner.os }}-sonar
          restore-keys: ${{ runner.os }}-sonar
      - name: Cache Maven packages
        uses: actions/cache@v3
        with:
          path: ~/.m2
          key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}
          restore-keys: ${{ runner.os }}-m2
      - name: Build and analyze
        working-directory: ./Deliverables/Sprint1/Sprint1_TruckMotion
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}  # Needed to get PR information, if any
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
        run: mvn -B verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -DskipITs -Dsonar.projectKey=RafaelFaisca32_desofs2024_M1C_3
      - name: Depcheck
        uses: dependency-check/Dependency-Check_Action@main
        id: Depcheck
        env:
          JAVA_HOME: /opt/jdk
        with:
          project: 'truckmotion'
          path: './Deliverables/Sprint1/Sprint1_TruckMotion'
          format: 'HTML'
          args: >
            --enableRetired
      - name: Upload Test results
        uses: actions/upload-artifact@master
        with:
          name: Depcheck report
          path: ${{github.workspace}}/reports
      - name: Login to Docker Hub
        uses: docker/login-action@v3
        with:
          username: ${{ secrets.DOCKER_USERNAME }}
          password: ${{ secrets.DOCKERHUB_TOKEN }}
      - name: Build Docker Image
        working-directory: ./Deliverables/Sprint1/Sprint1_TruckMotion
        run: 
          npm run java:docker
      - name: Push Docker Image
        run: |
          docker push ${{ secrets.DOCKER_HUB_REPO }}
      - name: Webhook for render deployment
        uses: distributhor/workflow-webhook@v3
        with:
          webhook_url: ${{ secrets.RENDER_WEBHOOK }}
```
I will now explain what every step does and some extra configuration.

> Secrets

- secrets.GITHUB_TOKEN: The GITHUB_TOKEN secret is a GitHub App installation access token.
- secrets.SONAR_TOKEN: This token is used in order to connect the SonarCloud with the Application.
- secrets.DOCKER_USERNAME: Username of the Docker Account that is used in order to login to the Docker Hub.
- secrets.DOCKERHUB_TOKEN: Token of the Docker Account that is used in order to login to the Docker Hub.
- secrets.DOCKER_HUB_REPO: Name of the Docker Hub Repository.
- secrets.RENDER_WEBHOOK: Link in order to trigger a new redeployment in Render.

> On property with the pipeline "if"

The On property is the setting that allows to select when is the workflow supposed to be executed. In this case, the workflow will be executed when there is a push or a pull request. The workflow_dispatch setting is used to be able to manually run the job for testing purposes.
The if setting is used in order to skip the workflow execution if needed when doing a push. So if you add __[ci skip]__ or __[skip ci]__ the pipeline won't be executed.

> Runs-on and working-directory

The Runs-on property is used to select which Virtual Machine will be used by GitHub Actions to execute the workflow.
The working-directory tag is used to move to a specific folder inside the repository (similar behavior to cd on terminal).

> actions/checkout@v4

This action checks-out your repository under $GITHUB_WORKSPACE, so your workflow can access it.

> actions/setup-node@v4

This action is used to download a specific node version and adding to the PATH of the VM.

> actions/setup-java@v4

This action is used to download a specific Java version and adding to the PATH of the VM.

> Install Node.js packages

In this step, the npm packages will be installed.

> Run backend test

In this step, the created backend unit tests will be ran.

> Run frontend test

In this step, the created frontend tests will be ran.

> Cache SonarCloud packages and Cache Maven packages

Both steps are used to cache the packages after the first run in order to save some time on the pipeline execution and speed up the process.

> Build and analyze

This step is used to build and analyze the application and it will execute SonarQube on top of the app and send the data to the SonarCloud server.

> Depcheck

This step is used to run a DependencyCheck over the Application in order to identify possible vulnerabilities related to app dependencies.

> Upload Test results

This step is used to upload the test results made by DependencyCheck as an artifact of the GitHub Actions.

> Login to Docker Hub

This step is used in order to login to Docker Hub in order to push the image of the app for deployment.

> Build Docker Image

This step builds the docker Image of the Application.
[Jib](https://github.com/GoogleContainerTools/jib) was used in order to create the docker image without the need of a Docker daemon.

> Push Docker Image

This step is used in order to push the image to the Docker Hub.

> Webhook for render deployment

This step is used to force a redeployment of the app with a webhook before finishing the workflow.

### ZAP Pipeline

```yaml
name: ZAP Pipeline
on: 
    workflow_dispatch:
jobs:
  pipeline:
    name: TruckMotion ZAP pipeline post-deployment
    runs-on: ubuntu-latest
    if: ${{ github.event_name == 'workflow_dispatch' }}
    timeout-minutes: 40
    env:
      JHI_DISABLE_WEBPACK_LOGS: true
    steps:
      - name: ZAP Scan
        uses: zaproxy/action-full-scan@v0.10.0
        with:
          token: ${{ secrets.GITHUB_TOKEN }}
          target: 'https://truckmotion.onrender.com/'
```

This pipeline will basically do an analysis to our newly deployed version of the application. It will also add the report of the results as an artifact to GitHub Actions.
The reason why this is separate and can only be ran manually (workflow_dispatch) is because the Render servers are extremely slow to start, so if it was on the other workflow file it wouldn't do the analysis properly.

## Deployment process

In order to deploy the application, a Docker image using Jib was created and was pushed to a repository on Docker Hub.
By using [Render](http://render.com) we are able to create Web Services that can deploy from existing images, in our case, the Docker Image that was pushed to a specific repository.
So after that, all we need to do is to force a redeploy of the Application every time there are changes pushed to Master, which is done by the step __"Webhook for render deployment"__.


