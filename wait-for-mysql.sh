#!/bin/sh

host="$1"
shift

echo "Waiting for $host to be ready..."

until nc -z "$host" 3306; do
  sleep 2
done

echo "$host is up — executing command"
exec "$@"