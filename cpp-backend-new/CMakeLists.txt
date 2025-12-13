cmake_minimum_required(VERSION 3.10)

project(cpp-backend-project)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_EXPORT_COMPILE_COMMANDS ON)

# Add Homebrew include path
if(APPLE)
    execute_process(
        COMMAND brew --prefix
        OUTPUT_VARIABLE BREW_PREFIX
        OUTPUT_STRIP_TRAILING_WHITESPACE
    )
    include_directories(${BREW_PREFIX}/include)
endif()

include_directories(include src)

file(GLOB_RECURSE SOURCES "src/*.cpp")

add_executable(cpp-backend-project ${SOURCES})

find_package(GTest REQUIRED)

enable_testing()
add_subdirectory(tests)