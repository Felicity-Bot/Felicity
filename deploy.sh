#!/bin/bash

sudo apt update
chmod +x gradlew
./gradlew build
java -jar build/libs/Felicity-Bot-1.0-SNAPSHOT.jar