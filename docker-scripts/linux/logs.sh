#!/bin/sh
docker compose -f ../../docker-compose.yaml logs -f --tail 100 authify-core