#!/bin/bash
./mvnw clean package && \
docker build -f src/main/docker/Dockerfile.jvm -t taaja/blueracoon . && \
docker save -o taaja-blueracoon.tar taaja/blueracoon && \
scp taaja-blueracoon.tar taaja@taaja.io:/home/taaja && \
ssh taaja@taaja.io 'docker load --quiet --input /home/taaja/taaja-blueracoon.tar && cd /home/taaja/deployment/taaja && docker-compose up -d'