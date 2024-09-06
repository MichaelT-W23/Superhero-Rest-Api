#!/bin/sh
YELLOW='\033[0;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color


# Check if port is in use and kill the process
kill_port_8080() {
  PID=$(lsof -t -i:8080)

  if [ -n "$PID" ]; then
    echo "${YELLOW}Port 8080 is in use by PID $PID. Stopping the process...${NC}"
    kill -9 $PID
    echo "${YELLOW}Process $PID has been stopped.${NC}"
  fi

}

stop_spring_boot() {
  echo "\n\n${YELLOW}Spring Boot application finished successfully!${NC}"
  kill "$SPRING_BOOT_PID"
}

trap stop_spring_boot INT TERM

# Check if port 8080 is in use and stop the process
kill_port_8080

# Run the Gradle bootRun task in the background
./gradlew --quiet bootRun &
SPRING_BOOT_PID=$!

sleep 0.1

echo "\n* Running on ${BLUE}http://localhost:8080${NC}\n"

wait "$SPRING_BOOT_PID"