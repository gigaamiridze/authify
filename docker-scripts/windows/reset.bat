@echo off
docker compose -f ..\..\docker-compose.yaml down -v
docker compose -f ..\..\docker-compose.yaml up --build -d