#!/bin/bash

echo "Starting redis server..."

docker run -p 6379:6379 --name my-redis -d redis redis-server --appendonly yes

