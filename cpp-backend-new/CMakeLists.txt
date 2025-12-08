cmake_minimum_required(VERSION 3.10)

project(cpp-backend-project)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_EXPORT_COMPILE_COMMANDS ON)

include_directories(include src)

file(GLOB_RECURSE SOURCES "src/*.cpp")

add_executable(cpp-backend-project ${SOURCES})

find_package(GTest REQUIRED)

enable_testing()
add_subdirectory(tests)