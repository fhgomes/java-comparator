# java-comparator

Application to compare two contents.

# Some considerations

I decided to assume that application will evolute and will able 
to compare another types of contents, not only JSON.

I also prepared the application decoupling the layers, 
maybe someday it will possible to simple replace some implementation or decouple 
some implementation creating new isolated services.

I tried to simulate a complex scene to exercise a little.

# Database

I choose to use embedded MongoDB to make it simpler to run in Dev mode.
Its an option to provide another Spring Profile that can turn it off and redirect to a real Mongo cluster

# How to Build and RUN

## Build Jar

    mvnw clean install -DskipTests

It will create a Jar file in ./boot/target

## RUN directly

In /boot/target

java -jar boot-1.0.0-SNAPSHOT.jar

## RUN in Docker

There are some sh files to make it easier.

Run image build:
    
    sh docker-build-image.sh
    
Run container with compose

    sh docker-run-compose.sh
    
It will run with exposed port 8080

# Run tests

I Decided to split the unit and integrations, because the integrations maybe become slow

## Run tests without Integration

The default profile is disabling the integration tests

    mvnw clean install
    
or just

    mvnw test
    
## Run with Integration tests

    mvnw clean install -Ptest_with_integration
    
or just

    mvnw test -Ptest_with_integration
    
## PLUS Implementation

Maybe you should take a look here https://github.com/fhgomes/java-comparator/tree/feature/assync_process

This version use JMS and start process when the second side is received.
The result is saved in database.

# Travis

[![Build Status](https://travis-ci.com/fhgomes/java-comparator.svg?branch=master)](https://travis-ci.com/fhgomes/java-comparator)