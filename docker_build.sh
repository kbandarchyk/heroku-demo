#!/bin/bash
docker run --rm -v ${PWD}:/usr/src/mymaven -w /usr/src/mymaven maven:3.6.3-jdk-11-slim  mvn -Pproduction clean package