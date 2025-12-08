#!/bin/bash

# Navigate to the project root (one level up from scripts/)
cd "$(dirname "$0")/.."

# Create and enter the build directory
mkdir -p build
cd build

# Compile the project
cmake .. && make

# Run the application
./cpp-backend-project