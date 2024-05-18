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
        working-directory: ./Sprint1_TruckMotion
        run: npm install
      - name: Run backend test
        working-directory: ./Sprint1_TruckMotion
        run: |
          chmod +x mvnw
          npm run ci:backend:test
      - name: Run frontend test
        working-directory: ./Sprint1_TruckMotion
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
        working-directory: ./Sprint1_TruckMotion
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
          path: './Sprint1_TruckMotion'
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
        working-directory: ./Sprint1_TruckMotion
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


