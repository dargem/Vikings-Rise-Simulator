cmake_minimum_required(VERSION 3.10)

project(cpp-backend-project)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_EXPORT_COMPILE_COMMANDS ON)

# Clang-tidy configuration
find_program(CLANG_TIDY_EXE NAMES "clang-tidy")
if(CLANG_TIDY_EXE)
    message(STATUS "clang-tidy found: ${CLANG_TIDY_EXE}")
    set(CMAKE_CXX_CLANG_TIDY "${CLANG_TIDY_EXE}")
else()
    message(WARNING "clang-tidy not found!")
endif()

# Add Homebrew include path
if(APPLE)
    execute_process(
        COMMAND brew --prefix
        OUTPUT_VARIABLE BREW_PREFIX
        OUTPUT_STRIP_TRAILING_WHITESPACE
    )
    include_directories(${BREW_PREFIX}/include)
endif()

include_directories(include)

# Define resource directory path
add_compile_definitions(RESOURCE_DIR=\"${CMAKE_CURRENT_SOURCE_DIR}/resources\")

file(GLOB_RECURSE SOURCES "src/*.cpp")

add_executable(cpp-backend-project ${SOURCES})

find_package(GTest REQUIRED)

enable_testing()
add_subdirectory(tests)