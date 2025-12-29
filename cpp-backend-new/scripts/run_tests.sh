#!/usr/bin/env bash
set -euo pipefail

# Resolve project root regardless of the caller's current working directory.
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="${SCRIPT_DIR%/scripts}"
BUILD_DIR="${ROOT_DIR}/build"

# Configure and build the project.
cmake -S "${ROOT_DIR}" -B "${BUILD_DIR}"
cmake --build "${BUILD_DIR}"

# Run the CTest suite (includes unit_tests target).
ctest --test-dir "${BUILD_DIR}" --output-on-failure