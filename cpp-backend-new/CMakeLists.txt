cmake_minimum_required(VERSION 3.14)

project(cpp-backend-project)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_EXPORT_COMPILE_COMMANDS ON)

# Clang-tidy configuration
find_program(CLANG_TIDY_EXE NAMES "clang-tidy")
if(CLANG_TIDY_EXE)
    message(STATUS "clang-tidy found: ${CLANG_TIDY_EXE}")
    set(CMAKE_CXX_CLANG_TIDY "${CLANG_TIDY_EXE};--header-filter=.*cpp-backend-new/(src|include)/.*")
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

    execute_process(
        COMMAND xcrun --show-sdk-path
        OUTPUT_VARIABLE MACOS_SDK_PATH
        OUTPUT_STRIP_TRAILING_WHITESPACE
    )
    
    include_directories(${BREW_PREFIX}/include)
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -isysroot ${MACOS_SDK_PATH}")
endif()

include_directories(include)

# Define resource directory path
add_compile_definitions(RESOURCE_DIR=\"${CMAKE_CURRENT_SOURCE_DIR}/resources\")

# Ensure nlohmann_json is available (prefer system, fallback to FetchContent).
include(FetchContent)
find_package(nlohmann_json 3.2.0 QUIET)
if(NOT nlohmann_json_FOUND)
    message(STATUS "nlohmann_json not found; fetching v3.11.3")
    FetchContent_Declare(
        nlohmann_json
        GIT_REPOSITORY https://github.com/nlohmann/json.git
        GIT_TAG v3.11.3
    )
    FetchContent_MakeAvailable(nlohmann_json)
endif()

file(GLOB_RECURSE SOURCES "src/*.cpp")

add_executable(cpp-backend-project ${SOURCES})
target_link_libraries(cpp-backend-project PRIVATE nlohmann_json::nlohmann_json)

find_package(GTest REQUIRED)

enable_testing()
add_subdirectory(tests)