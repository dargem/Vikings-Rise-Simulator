cmake_minimum_required(VERSION 3.10)

project(cpp-backend-project)

set(CMAKE_CXX_STANDARD 17)

include_directories(include)

file(GLOB SOURCES "src/*.cpp")

add_executable(cpp-backend-project ${SOURCES})

find_package(GTest REQUIRED)

target_link_libraries(your_test_target GTest::GTest GTest::Main)

enable_testing()
add_subdirectory(tests)