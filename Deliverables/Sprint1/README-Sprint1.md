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
