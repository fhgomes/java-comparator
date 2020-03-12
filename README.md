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

## Build

    mvnw clean install -DskipTests

It will create a Jar file in ./boot/target

## RUN

In /boot/target

java -jar boot-1.0.0-SNAPSHOT.jar

# Run tests

I Decided to split the unit and integrations, because the integrations maybe become slow

## Run without Integration

The default profile is disabling the integration tests

    mvnw clean install
    
or just

    mvnw test
    
## Run with Integration tests

    mvnw clean install -Ptest_with_integration
    
or just

    mvnw test -Ptest_with_integration
    