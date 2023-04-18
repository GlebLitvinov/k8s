# K8s Homework EPAM

## Overview

This repository contains my HW for EPAM Kubernetes for Java Engineers course 

## HW01

[Description](https://git.epam.com/Siarhei_Svila/kubernetes-mentoring-program/-/blob/main/1-microservices-architecture-and-docker/task/README.md)

To verify that everything works:
 - Replace `epamhleblitvinau` with your docker hub login in [build users](users/rebuild-docker.sh), [build posts](posts/rebuild-docker.sh), [docker compose](docker-compose.yaml) files
  - Run [build users](users/rebuild-docker.sh) and [build posts](posts/rebuild-docker.sh) 
 - Run `docker compose up` from root directory
 - Wait until everything is up and running (services might restart couple times because init scripts are not finished and DBs are not created yet)
 - Run [e2e test](e2e-test.http)

## Repository Structure

Top level directories:
- `users` - users related functionality
- `posts` - posts related functionality
- `init` - init scripts for mysql
