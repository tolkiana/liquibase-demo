#!/usr/bin/env bash

echo "Stopping postgres image..."

docker stop pg-docker

set -e

echo "Pulling postgres 11 image..."

docker pull postgres:11

echo "Starting postgres image..."

docker run --rm   --name pg-docker -e POSTGRES_PASSWORD=liquibase -d -p 5432:5432 postgres
